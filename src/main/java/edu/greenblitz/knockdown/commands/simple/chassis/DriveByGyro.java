package edu.greenblitz.knockdown.commands.simple.chassis;

import edu.greenblitz.knockdown.data.GearDependentDouble;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Shifter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.greenblitz.motion.base.Position;

public class DriveByGyro extends ChassisBaseCommand implements PIDSource, PIDOutput {

    // NOTE: NOT PID ANYMORE, JUST PASSES MAX VALUE
    private static final GearDependentDouble
            kP = new GearDependentDouble(Shifter.Gear.SPEED, 0.3),
            kI = new GearDependentDouble(Shifter.Gear.SPEED, 0),
            kD = new GearDependentDouble(Shifter.Gear.SPEED, 0);

    private static final GearDependentDouble TURN_P = new GearDependentDouble(Shifter.Gear.SPEED, 0.003);

    private static final long TIME_ON_TARGET = 200;

    private static final GearDependentDouble POWER_LIMIT = new GearDependentDouble(0.4, 0.4);

    private long m_onTarget = -1;
    private double m_distance, m_angle;

    private PIDController m_controller;

    private GearDependentDouble maxVelocity = POWER_LIMIT;
    private GearDependentDouble p = kP, i = kI, d = kD;
    private boolean m_stopAtEnd;
    private boolean m_useVisionAngle;

    private static String generateName(double distance) {
        return DriveByGyro.class.getSimpleName() + " for {" + distance + "}";
    }

    public DriveByGyro(double distance, long ms, boolean stopAtEnd, boolean useVisionAngle) {
        super(generateName(distance), ms);

        m_distance = distance;
        m_controller = new PIDController(0, 0, 0, this, this);

        m_controller.setAbsoluteTolerance(0.1);
        m_stopAtEnd = stopAtEnd;
        m_useVisionAngle = useVisionAngle;
    }

    public DriveByGyro(double distance, long ms, boolean stopAtEnd) {
        this(distance, ms, stopAtEnd, true);
    }

    public DriveByGyro(double distance, long ms, GearDependentDouble maxVelocity) {
        this(distance, ms);
        this.maxVelocity = maxVelocity;
    }

    public DriveByGyro(double distance, long ms, GearDependentDouble maxVelocity, boolean stop) {
        this(distance, ms, maxVelocity);
        m_stopAtEnd = stop;
    }

    public DriveByGyro(double distance, long ms) {
        this(distance, ms, false);
    }

    @Override
    protected void atInit() {
        m_angle = m_useVisionAngle ? VisionMaster.getInstance().getLastAngleToDrive() : Chassis.getInstance().getAngle();

        m_controller.setSetpoint(Chassis.getInstance().getDistance() + m_distance);

        var current = Shifter.getInstance().getCurrentGear();
        var limit = maxVelocity.getByGear(current);
        m_controller.setOutputRange(-limit, limit);

        m_controller.setPID(p.getByGear(current), i.getByGear(current), d.getByGear(current));

        m_controller.enable();
    }

    @Override
    public void pidWrite(double output) {
        Chassis.getInstance().arcadeDrive(Math.signum(output)*maxVelocity.getByCurrentGear(), pidOverAngle());
    }

    private double pidOverAngle() {
        return Math.toDegrees(Position.normalizeAngle(Math.toRadians(m_angle - Chassis.getInstance().getAngle()))) * TURN_P.getByCurrentGear();
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return Chassis.getInstance().getDistance();
    }

    @Override
    protected boolean isFinished() {
        if (m_controller.onTarget()) {
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
        } else
            m_onTarget = -1;

        return isTimedOut() || (m_onTarget != -1 && m_controller.onTarget() && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET);
    }

    @Override
    protected void atEnd() {
        m_controller.disable();
        logger.debug("PID error: {}", m_controller.getError());
        if (m_stopAtEnd) Chassis.getInstance().stop();
    }

    @Override
    public String toString() {
        return "DriveByGyro{" +
                "distance=" + m_distance +
                ", angle=" + m_angle +
                '}';
    }
}
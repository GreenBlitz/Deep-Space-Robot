package edu.greenblitz.knockdown.commands.simple.chassis.vision;

import edu.greenblitz.knockdown.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.knockdown.data.GearDependentDouble;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Shifter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToDistanceFromVisionTarget extends ChassisBaseCommand implements PIDSource, PIDOutput {

    private static final double VISION_TARGET_OFFSET = 0;

    private static final GearDependentDouble
            kP = new GearDependentDouble(Shifter.Gear.SPEED, 0.3),
            kI = new GearDependentDouble(Shifter.Gear.SPEED, 0),
            kD = new GearDependentDouble(Shifter.Gear.SPEED, 0);

    private static final GearDependentDouble POWER_LIMIT = new GearDependentDouble(.6, 0.25);

    private static final GearDependentDouble turnKp = new GearDependentDouble(Shifter.Gear.SPEED, 0.0030);

    private double m_distance;
    private double m_visionTargetOffset;

    private PIDController m_controller;
    private boolean m_stopAtEnd;

    private static String generateName(double distance) {
        return DriveToDistanceFromVisionTarget.class.getSimpleName() + " for {" + distance + "}";
    }

    public DriveToDistanceFromVisionTarget(double distance, double visionTargetOffset, boolean stopAtEnd) {
        super(generateName(distance));
        m_distance = distance;
        m_controller = new PIDController(0, 0, 0, this, this);
        m_stopAtEnd = stopAtEnd;
        m_visionTargetOffset = visionTargetOffset;
    }



    public DriveToDistanceFromVisionTarget(double distance) {
        this(distance, VISION_TARGET_OFFSET, false);
    }

    public DriveToDistanceFromVisionTarget(double distance, double visionTargetOffset) {
        this(distance, visionTargetOffset, false);
    }

    @Override
    protected void atInit() {
        System.out.println("vision");
        var currentGear = Shifter.getInstance().getCurrentGear();

        var limit = POWER_LIMIT.getByGear(currentGear);
        var p = kP.getByGear(currentGear);
        var i = kI.getByGear(currentGear);
        var d = kD.getByGear(currentGear);

        m_controller.setOutputRange(-limit, limit);
        m_controller.setPID(p, i, d);
        m_controller.setSetpoint(m_distance/2);
        m_controller.setAbsoluteTolerance(m_distance/3);

        VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
        m_controller.enable();
    }

    @Override
    public void pidWrite(double output) {
//        if (VisionMaster.getInstance().isDataValid())
            Chassis.getInstance().arcadeDrive(-output, (VisionMaster.getInstance().getAngle() + m_visionTargetOffset)*turnKp.getByCurrentGear());
//        else
//            Chassis.getInstance().stop();
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {}

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return VisionMaster.getInstance().getPlaneryDistance();
    }

    @Override
    protected boolean isFinished() {
        return VisionMaster.getInstance().isDataValid() && m_controller.onTarget();
    }

    @Override
    protected void atEnd() {
        System.out.println("done");
        VisionMaster.getInstance().updateLastAngleToDrive(m_visionTargetOffset);
        m_controller.disable();
        if (m_stopAtEnd) Chassis.getInstance().stop();
//        else Chassis.getInstance().tankDrive();
    }
}
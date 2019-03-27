//package edu.greenblitz.robotname.commands.simple.chassis.vision;
//
//import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
//import edu.greenblitz.robotname.data.vision.VisionMaster;
//import edu.greenblitz.robotname.subsystems.Chassis;
//import org.greenblitz.motion.pid.MultivariablePIDController;
//import org.greenblitz.motion.pid.PIDObject;
//import org.greenblitz.motion.tolerance.AbsoluteTolerance;
//import org.greenblitz.motion.tolerance.ITolerance;
//
//public class DriveToDistanceFromVisionTarget extends ChassisBaseCommand {
//
//    private static final int DRIVE_IDX = 0;
//    private static final int TURN_IDX = 1;
//
//    private static final PIDObject DRIVE = new PIDObject(0.75, 0, 0);
//    private static final PIDObject TURN = new PIDObject(0.02, 0, 0);
//
//    private final ITolerance DRIVE_TOL;
//    private static final ITolerance TURN_TOL = new AbsoluteTolerance(3);
//
//    private static final double POWER_LIMIT = 0.4;
//
//    private MultivariablePIDController m_controller;
//
//    private static final long TIME_ON_TARGET = 1000;
//    private long m_onTarget = -1;
//
//    private double m_distance;
//
//    public DriveToDistanceFromVisionTarget(double distance) {
//        m_distance = distance;
//        DRIVE_TOL = new AbsoluteTolerance(m_distance/2);
//
//        m_controller = new MultivariablePIDController(2);
//        m_controller.setPIDObject(DRIVE_IDX, DRIVE, DRIVE_TOL);
//        m_controller.setPIDObject(TURN_IDX, TURN, TURN_TOL);
//      }
//
//    @Override
//    protected void initialize() {
//        VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
//        var state = VisionMaster.getInstance().getStandardizedData();
//        var inputDrive = state.getPlaneryDistance();
//        var inputTurn = state.getRelativeAngle();
//
//        m_onTarget = -1;
//        m_controller.setGoals(0, 0);
//
//        m_controller.configurePID(DRIVE_IDX, inputDrive, m_distance, -POWER_LIMIT, POWER_LIMIT, 0);
//        m_controller.configurePID(TURN_IDX, inputTurn, 0, -POWER_LIMIT, POWER_LIMIT, 0);
//    }
//
//    @Override
//    protected void execute() {
//        var state = VisionMaster.getInstance().getStandardizedData();
//        var inputDrive = state.getPlaneryDistance();
//        var inputTurn = state.getRelativeAngle();
//        var pidResult = m_controller.calculate(inputDrive, inputTurn);
//
//        Chassis.getInstance().arcadeDrive(-pidResult[DRIVE_IDX], -pidResult[TURN_IDX]);
//
//        if (m_controller.isFinished(new double[]{inputDrive, inputTurn})) {
//            if (m_onTarget == -1)
//                m_onTarget = System.currentTimeMillis();
//        } else {
//            m_onTarget = -1;
//        }
//    }
//
//    @Override
//    protected boolean isFinished() {
//        var state = VisionMaster.getInstance().getStandardizedData();
//        var inputDrive = state.getPlaneryDistance();
//        var inputTurn = state.getRelativeAngle();
//        boolean ret1 = m_controller.isFinished(new double[]{inputDrive, inputTurn});
//        boolean ret2 = System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
//        return ret1 && ret2;
//    }
//
//    @Override
//    protected void atEnd() {
//        Chassis.getInstance().stop();
//    }
//}

package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.GearDependentDouble;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveToDistanceFromVisionTarget extends ChassisBaseCommand implements PIDSource, PIDOutput {

    private static final double VISION_TARGET_OFFSET = 0;

    private static final GearDependentDouble
            kP = new GearDependentDouble(Shifter.Gear.SPEED, 0.3),
            kI = new GearDependentDouble(Shifter.Gear.SPEED, 0),
            kD = new GearDependentDouble(Shifter.Gear.SPEED, 0);

    private static final GearDependentDouble POWER_LIMIT = new GearDependentDouble(.8, 0.25);

    private static final GearDependentDouble turnKp = new GearDependentDouble(Shifter.Gear.SPEED, 0.0035);

    private static final long TIME_ON_TARGET = 0;

    private long m_onTarget = -1;
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
        if (m_controller.onTarget()) {
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
        }
        else
            m_onTarget = -1;

        return VisionMaster.getInstance().isDataValid() && (m_onTarget != -1 && m_controller.onTarget() && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET);
    }

    @Override
    protected void atEnd() {
        m_controller.disable();
        if (m_stopAtEnd) Chassis.getInstance().stop();
    }
}
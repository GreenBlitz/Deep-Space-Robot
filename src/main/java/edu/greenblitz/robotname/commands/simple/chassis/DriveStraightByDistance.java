//package edu.greenblitz.robotname.commands.simple.chassis;
//
//import edu.greenblitz.robotname.subsystems.Chassis;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.greenblitz.motion.pid.MultivariablePIDController;
//import org.greenblitz.motion.pid.PIDObject;
//import org.greenblitz.motion.tolerance.AbsoluteTolerance;
//import org.greenblitz.motion.tolerance.ITolerance;
//
//public class DriveStraightByDistance extends ChassisBaseCommand {
//
//    private static final int DRIVE_IDX = 0;
//    private static final int TURN_IDX = 1;
//
//    private static final PIDObject DRIVE = new PIDObject(0.25, 0, 0);
//    private static final PIDObject TURN = new PIDObject(0.01, 0, 0);
//
//    private static final ITolerance DRIVE_TOL = new AbsoluteTolerance(0.1);
//    private static final ITolerance TURN_TOL = new AbsoluteTolerance(3);
//
//    private static final double POWER_LIMIT = 0.3;
//
//    private MultivariablePIDController m_controller;
//
//    private static final long TIME_ON_TARGET = 1000;
//    private long m_onTarget = -1;
//
//    private double m_distance;
//
//    public DriveStraightByDistance(double distance, long ms) {
//        super(ms);
//        m_distance = distance;
//
//        m_controller = new MultivariablePIDController(2);
//        m_controller.setPIDObject(DRIVE_IDX, DRIVE, DRIVE_TOL);
//        m_controller.setPIDObject(TURN_IDX, TURN, TURN_TOL);
//    }
//
//    public DriveStraightByDistance(double distance) {
//        m_distance = distance;
//
//        m_controller = new MultivariablePIDController(2);
//        m_controller.setPIDObject(DRIVE_IDX, DRIVE, DRIVE_TOL);
//        m_controller.setPIDObject(TURN_IDX, TURN, TURN_TOL);
//    }
//
//    @Override
//    protected void initialize() {
//        var inputDrive = Chassis.getInstance().getDistance();
//        var inputTurn = Chassis.getInstance().getAngle();
//
//        m_onTarget = -1;
//
//        m_controller.configurePID(DRIVE_IDX, inputDrive, inputDrive + m_distance, -POWER_LIMIT, POWER_LIMIT, 0);
//        m_controller.configurePID(TURN_IDX, inputTurn, inputTurn, -POWER_LIMIT/2, POWER_LIMIT/2, 0);
//    }
//
//    @Override
//    protected void execute() {
//        var inputDrive = Chassis.getInstance().getDistance();
//        var inputTurn = Chassis.getInstance().getAngle();
//        var pidResult = m_controller.calculate(inputDrive, inputTurn);
//
//        Chassis.getInstance().arcadeDrive(pidResult[DRIVE_IDX], -pidResult[TURN_IDX]);
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
//        var inputDrive = Chassis.getInstance().getDistance();
//        var inputTurn = Chassis.getInstance().getAngle();
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

package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveStraightByDistance extends ChassisBaseCommand implements PIDSource, PIDOutput {

    private static final double Kp = 0.35, Ki = 0, Kd = 0;
    private static final double turnKp = 0.06/25;

    private static final long TIME_ON_TARGET = 200;

    private long m_onTarget = -1;
    private double m_distance, m_angle;

    private PIDController m_controller;

    private static String generateName(double distance) {
        return DriveStraightByDistance.class.getSimpleName() + " for {" + distance + "}";
    }

    public DriveStraightByDistance(double distance, double min, double max, double tolerance, double kP) {
        super(generateName(distance));
        m_distance = distance;
        m_controller = new PIDController(kP, 0, 0, this, this);
        m_controller.setOutputRange(min, max);
        m_controller.setAbsoluteTolerance(tolerance);
    }

    public DriveStraightByDistance(double distance) {
        this(distance, Kp, -0.15, 0.15, 0.1);
    }

    public DriveStraightByDistance(double distance, long ms) {
        super(generateName(distance), ms);

        m_distance = distance;
        m_controller = new PIDController(Kp, Ki, Kd, this, this);
        m_controller.setOutputRange(-0.15, 0.15);
        m_controller.setAbsoluteTolerance(0.1);
    }

    @Override
    protected void initialize() {
        m_angle = Chassis.getInstance().getAngle();
        m_controller.setSetpoint(Chassis.getInstance().getDistance() + m_distance);

        m_controller.enable();
    }

    @Override
    public void pidWrite(double output) {
        Chassis.getInstance().arcadeDrive(output, (m_angle - Chassis.getInstance().getAngle())*turnKp);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {}

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
        }
        else
            m_onTarget = -1;

        return isTimedOut() || (m_onTarget != -1 && m_controller.onTarget() && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET);
    }

    @Override
    protected void atEnd() {
        m_controller.disable();
        Chassis.getInstance().stop();
    }

    @Override
    public String toString() {
        return "DriveStraightByDistance{" +
                "distance=" + m_distance +
                ", angle=" + m_angle +
                '}';
    }
}
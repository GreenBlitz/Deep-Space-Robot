package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.StandardVisionData;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class ArcPidDriveToVisionTarget extends ChassisBaseCommand {

    private static final double SLOWDOWN_ANGLE = 30;
    private static final double POWER_AT_SLOWDOWN = 0.2;
    private static final PIDObject PID_CONFIG = new PIDObject(POWER_AT_SLOWDOWN / SLOWDOWN_ANGLE, 0, 0);
    private static final double FULL_POWER = 0.4;
    private static final double DEADBAND = 0;
    private static final ITolerance PID_TOLERANCE = new AbsoluteTolerance(Math.toRadians(5));

    private PIDController m_controller;
    private int m_onTarget;


    private StandardVisionData data;

    private double curvature;
    private Point endLocation;

    public ArcPidDriveToVisionTarget(StandardVisionData data) {
        this.data = data;
        m_controller = new PIDController(PID_CONFIG, PID_TOLERANCE);
    }

    private static final double EPSILON = 1E-2;

    @Override
    protected void atInit() {
        data = VisionMaster.getInstance().getStandardizedData()[0];
        double targetX = data.x;
        double targetY = data.z;
        curvature = getCurvature(targetX, targetY);
        endLocation = system.getLocation().translate(new Point(targetX, targetY).rotate(-system.getAngle()));
        m_controller.configure(get(), 0, -FULL_POWER, FULL_POWER, DEADBAND);
        m_onTarget = 0;
    }

    @Override
    protected void execute() {
        double pidValue = m_controller.calculatePID(get());
        data = VisionMaster.getInstance().getStandardizedData()[0];
        double targetX = data.x;
        double targetY = data.z;
        curvature = getCurvature(targetX, targetY);
        Vector2D driveValues = arcDrive(curvature, 0.1);
        logger.debug(driveValues);
        set(driveValues.getX(), driveValues.getY(), pidValue);
    }

    @Override
    protected boolean isFinished() {
        return Point.fuzzyEquals(system.getLocation(), endLocation, EPSILON) && isPidFinished();
    }


    private double getCurvature(double dx, double dy) {
        if (Double.isNaN(dx) || Double.isNaN(dy))
            throw new IllegalArgumentException("invalid argument : NaN");
        return -2 * dx / Math.hypot(dx, dy);
    }

    private Vector2D arcDrive(double curvature, double speed) {
        if (curvature == 0)
            return new Vector2D(speed, speed);
        double radius = 1 / curvature;
        double rightRadius = radius + RobotMap.Chassis.Data.WHEEL_BASE_RADIUS / 2;
        double leftRadius = radius - RobotMap.Chassis.Data.WHEEL_BASE_RADIUS / 2;
        if (curvature > 0)
            return new Vector2D(speed * leftRadius / rightRadius, speed);
        else
            return new Vector2D(speed, speed * rightRadius / leftRadius);
    }

    private boolean isPidFinished() {
        if (m_controller.isFinished(get()))
            m_onTarget++;
        else
            m_onTarget = 0;
        return m_onTarget >= 4;
    }

    private double getDesiredAngle() {
        StandardVisionData target = VisionMaster.getInstance().getStandardizedData()[0];
        return system.getAngle() + 2 * target.getCenterAngle() + target.getRelativeAngle();
    }

    private double get() {
//        return getDesiredAngle() - system.getAngle();
        return system.getAngle() - getDesiredAngle();
    }

    private void set(double left, double right, double turn) {
        system.tankDrive(left + turn, right - turn);
    }
}

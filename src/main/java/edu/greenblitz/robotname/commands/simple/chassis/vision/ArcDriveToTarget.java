package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.Vector2D;

public class ArcDriveToTarget extends ChassisBaseCommand {

    private double curvature;
    private Point endLocation;

    private static final double EPSILON = 1E-2;

    @Override
    protected void atInit(){
        double[] difference = VisionMaster.getInstance().getCurrentVisionData();
        double targetX = difference[0];
        double targetY = difference[2];
        curvature = getCurvature(targetX, targetY);
        endLocation = system.getLocation().translate(new Point(targetX, targetY).rotate(-system.getAngle()));
    }

    @Override
    protected void execute(){
        Vector2D driveValues = arcDrive(curvature, 0.1);
        logger.debug(driveValues);
//        system.tankDrive(driveValues.getX(), driveValues.getY());
    }

    @Override
    protected boolean isFinished() {
        return Point.fuzzyEquals(system.getLocation(), endLocation, EPSILON);
    }


    public double getCurvature(double dx, double dy) {
//        Point diff = Point.subtract(goalPoint, robotLoc).rotate(-robotLoc.getAngle()); // This line is here because if the robot
        // goes backwards he is "facing" the opposite direction
        return 2 * dx / Math.hypot(dx, dy);
    }

    protected Vector2D arcDrive(double curvature, double speed) {
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
}

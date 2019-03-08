package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import org.greenblitz.motion.base.Vector2D;

public class ArcDriveToTarget extends ChassisBaseCommand {

    private double targetX, targetY;

    @Override
    protected void initialize(){
        double[] difference = VisionMaster.getInstance().getCurrentVisionData();
        targetX = difference[0];
        targetY = difference[2];
    }

    @Override
    protected void execute(){

    }

    @Override
    protected boolean isFinished() {
        return false;
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

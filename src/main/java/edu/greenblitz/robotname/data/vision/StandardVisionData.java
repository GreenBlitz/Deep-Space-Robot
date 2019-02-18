package edu.greenblitz.robotname.data.vision;

public class StandardVisionData {
    /**
     * X coordinate of the target
     */
    public double x;

    /**
     * Y coordinate of the data
     */
    public double y;

    /**
     * The angle between the line between the target center and robot center, to the normal to the robot center
     */
    public double centerAngle;

    /**
     * The angle of the target, relative to the robot
     */
    public double angle;

    public StandardVisionData(double x, double y, double centerAngle, double angle) {
        this.x = x;
        this.y = y;
        this.centerAngle = centerAngle;
        this.angle = angle;
    }

    public StandardVisionData(double[] rawData) {
        this(rawData[0], rawData[1], rawData[2], rawData[3]);
    }
}

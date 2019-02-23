package edu.greenblitz.robotname.data.vision;

public class StandardVisionData {
    /**
     * X coordinate of the target (horizontal distance)
     */
    public double x;

    /**
     * Y coordinate of the data (vertical distance)
     */
    public double y;

    /**
     * Z coordinate of the data (depth)
     */
    public double z;

    /**
     * The angle of the target, relative to the robot
     */
    public double angle;

    public StandardVisionData(double x, double y, double z, double angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }

    public double getDistance() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     *
     * @return distance to target ignoring y component
     */
    public double getPlaneryDistance() {
        return Math.hypot(x, z);
    }

    public double getRelativeAngle() {
        return angle;
    }

    public double getCenterAngle() {
        return Math.atan2(z, x);
    }

    public StandardVisionData(double[] rawData) {
        this(rawData[0], rawData[1], rawData[2], rawData[3]);
    }
}

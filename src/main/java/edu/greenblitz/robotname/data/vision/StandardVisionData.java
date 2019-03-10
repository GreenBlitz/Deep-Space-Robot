package edu.greenblitz.robotname.data.vision;

import java.text.DecimalFormat;

public class StandardVisionData {
    public static final double DISTANCE_TOO_CLOSE = 4;

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
        return Math.toDegrees(angle);
    }

    public double getCenterAngle() {
        return Math.toDegrees(Math.atan2(x, z));
    }

    public StandardVisionData(double[] rawData) {
        this(rawData[0], rawData[1], rawData[2], rawData[3]);
    }

    public boolean isValid() {
        return Double.isFinite(x) && Double.isFinite(y) && Double.isFinite(z) && Double.isFinite(angle);
    }

    public boolean isTooClose() {
        return getDistance() > DISTANCE_TOO_CLOSE;
    }

    @Override
    public String toString() {
        DecimalFormat fmt = new DecimalFormat("###.####");
        return "StandardVisionData{" +
                "x=" + fmt.format(x) +
                ", y=" + fmt.format(y) +
                ", z=" + fmt.format(z) +
                ", angle=" + fmt.format(angle) +
                '}';
    }
}

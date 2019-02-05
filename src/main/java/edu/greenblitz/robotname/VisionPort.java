package edu.greenblitz.robotname;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionPort {

    private static VisionPort instance;
    public static VisionPort getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) instance = new VisionPort();
    }

    private NetworkTable visionTable;

    public VisionPort() {
        visionTable = NetworkTableInstance.getDefault().getTable("vision");
    }

    public double getHatchAngle() {
        return visionTable.getEntry("hatch::angle").getDouble(0);
    }

    public double getHatchDistance() {
        return visionTable.getEntry("hatch::distance").getDouble(0);
    }

    public NetworkTable getVisionTable() {
        return  visionTable;
    }
}

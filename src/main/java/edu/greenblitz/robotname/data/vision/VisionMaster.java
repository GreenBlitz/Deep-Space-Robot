package edu.greenblitz.robotname.data.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionMaster {

    public enum Algorithm {
        CARGO("send_cargo"),
        TARGETS("send_vision_targets"),
        HATCH("send_hatch");

        public final String rawAlgorithmName;

        Algorithm(String rawAlgorithmName) {
            this.rawAlgorithmName = rawAlgorithmName;
        }

        public String getRawName() {
            return rawAlgorithmName;
        }
    }

    private static VisionMaster instance;

    public static VisionMaster getInstance() {
        return instance;
    }

    public static void init() {
        if (instance == null) instance = new VisionMaster();
    }

    private NetworkTable m_visionTable;
    private NetworkTableEntry m_algorithm;
    private NetworkTableEntry m_values;

    public VisionMaster() {
        m_visionTable = NetworkTableInstance.getDefault().getTable("vision");
        m_algorithm = m_visionTable.getEntry("algorithm");
        m_values = m_visionTable.getEntry("output");
    }

    public void setCurrentAlgorithm(Algorithm algo) {
        m_algorithm.setString(algo.getRawName());
    }

    public double[] getCurrentVisionData() {
        return m_values.getValue().getDoubleArray();
    }

    public StandardVisionData getStandardizedData() {
        return new StandardVisionData(getCurrentVisionData());
    }

    public void getCurrentVisionData(double[] dest) {
        m_values.getDoubleArray(dest);
    }
}

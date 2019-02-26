package edu.greenblitz.robotname.data.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class VisionMaster {

    public enum Algorithm {
        CARGO("send_cargo"),
        TARGETS("send_vision_targets"),
        HATCH("send_hatch"),
        RAMP("send_ramp");

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
    private Logger logger;

    public VisionMaster() {
        logger = LogManager.getLogger(getClass());
        m_visionTable = NetworkTableInstance.getDefault().getTable("vision");
        m_algorithm = m_visionTable.getEntry("algorithm");
        m_values = m_visionTable.getEntry("output");
    }

    public void setCurrentAlgorithm(Algorithm algo) {
        m_algorithm.setString(algo.getRawName());
    }

    public double[] getCurrentVisionData() {
        if (m_values.getType() != NetworkTableType.kDoubleArray){
            logger.warn("vision value not double array");
            return new double[]{0, 0, 0, 0};
        }
        var ret = m_values.getValue().getDoubleArray();
        if (ret.length > 4) {
            logger.warn("vision returned more than 4 values");
            return new double[]{0, 0, 0, 0};
        }
        return ret;
    }

    public StandardVisionData getStandardizedData() {
        return new StandardVisionData(getCurrentVisionData());
    }

    public double getDistance() {
        return getStandardizedData().getDistance();
    }

    public double getPlaneryDistance() {
        return getStandardizedData().getPlaneryDistance();
    }

    public double getRelativeAngle() {
        return getStandardizedData().getRelativeAngle();
    }

    public double getAngle() {
        return getStandardizedData().getCenterAngle();
    }

    public void getCurrentVisionData(double[] dest) {
        m_values.getDoubleArray(dest);
    }

    public void setVisionAlgorithm(Algorithm algo) {
        m_visionTable.getEntry("algorithm").setString(algo.getRawName());
    }
}

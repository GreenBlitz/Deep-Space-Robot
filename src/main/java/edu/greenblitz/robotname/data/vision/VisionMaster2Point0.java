package edu.greenblitz.robotname.data.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * able to handle more that one at a time
 */
public class VisionMaster2Point0 {

    public enum Algorithm {
        CARGO("send_cargo"),
        TARGETS("send_hatch"),
        HATCH("send_hatch_panel"),
        RAMP("send_ramp");

        public final String rawAlgorithmName;

        Algorithm(String rawAlgorithmName) {
            this.rawAlgorithmName = rawAlgorithmName;
        }

        public String getRawName() {
            return rawAlgorithmName;
        }
    }

    public enum Error {
        NOT_ARRAY("vision value not double array"),
        UNEXPECTED_LENGTH("vision returned other than 4 values"),
        OK("");

        private final String msg;

        Error(String msg) {
            this.msg = msg;
        }


        @Override
        public String toString() {
            return msg;
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
    private Error m_lastError = Error.OK;

    public VisionMaster2Point0() {
        logger = LogManager.getLogger(getClass());
        m_visionTable = NetworkTableInstance.getDefault().getTable("vision");
        m_algorithm = m_visionTable.getEntry("algorithm");
        m_values = m_visionTable.getEntry("output");
    }

    public void setCurrentAlgorithm(Algorithm algo) {
        m_algorithm.setString(algo.getRawName());
    }

    public double[] getCurrentVisionData() {
        if (m_values.getType() != NetworkTableType.kDoubleArray) {
            if (m_lastError != Error.NOT_ARRAY) {
                logger.warn(Error.NOT_ARRAY);
                m_lastError = Error.NOT_ARRAY;
            }
            return null;
        }
        var ret = m_values.getValue().getDoubleArray();
        if (ret.length == 0 || ret.length % 4 != 0) {
            if (m_lastError != Error.UNEXPECTED_LENGTH) {
                logger.warn(Error.UNEXPECTED_LENGTH);
                m_lastError = Error.UNEXPECTED_LENGTH;
            }
            return null;
        }

        m_lastError = Error.OK;
        return ret;
    }

    public Error getLastError() {
        return m_lastError;
    }

    public StandardVisionData[] getStandardizedData() {
        double[] input = getCurrentVisionData();
        StandardVisionData[] ret = new StandardVisionData[input.length / 4];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new StandardVisionData(input[4 * i], input[4 * i + 1], input[4 * i + 2], input[4 * i + 3]);
        }
        return ret;
    }

    /**
     * @deprecated Do not use this unless you know what you are doing. I don't even know why this function exists
     * other than to confuse, since I cannot think of a valid use for this.
     */
    @Deprecated
    public double getDistance(int ind) {
        return getStandardizedData()[ind].getDistance();
    }

    public double getPlaneryDistance(int ind) {
        return getStandardizedData()[ind].getPlaneryDistance();
    }

    public double getRelativeAngle(int ind) {
        return getStandardizedData()[ind].getRelativeAngle();
    }

    public double getAngle(int ind) {
        return getStandardizedData()[ind].getCenterAngle();
    }

    public void getCurrentVisionData(double[] dest) {
        m_values.getDoubleArray(dest);
    }

    public void update() {
//        SmartDashboard.putNumber("Vision::Angle", getAngle());
//        SmartDashboard.putNumber("Vision::RelativeAngle", getRelativeAngle());
//        SmartDashboard.putNumber("Vision::Distance", getPlaneryDistance());
//        SmartDashboard.putBoolean("Vision::IsTooClose", getStandardizedData().isTooClose());
//        SmartDashboard.putBoolean("Vision::IsValid", getStandardizedData().isValid());
    }

}
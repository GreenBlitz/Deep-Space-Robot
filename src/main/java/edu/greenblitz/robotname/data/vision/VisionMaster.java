package edu.greenblitz.robotname.data.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            if (m_lastError != Error.NOT_ARRAY) {
                logger.warn(Error.NOT_ARRAY);
                m_lastError = Error.NOT_ARRAY;
            }
            return new double[]{0, 0, 0, 0};
        }
        var ret = m_values.getValue().getDoubleArray();
        if (ret.length != 4) {
            if (m_lastError != Error.UNEXPECTED_LENGTH) {
                logger.warn(Error.UNEXPECTED_LENGTH);
                m_lastError = Error.UNEXPECTED_LENGTH;
            }
            return new double[]{0, 0, 0, 0};
        }

        m_lastError = Error.OK;
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

    public void update() {
        SmartDashboard.putNumber("Vision::Angle", getAngle());
        SmartDashboard.putNumber("Vision::RelativeAngle", getRelativeAngle());
        SmartDashboard.putNumber("Vision::Distance", getPlaneryDistance());
    }

}
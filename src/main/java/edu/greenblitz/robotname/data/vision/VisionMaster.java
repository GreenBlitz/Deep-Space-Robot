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
public class VisionMaster {

    private static final double CAMERA_X_OFFSET = 0.07;

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

    public enum Focus {
        LEFT("left"),
        MIDDLE("middle"),
        RIGHT("right");

        public final String rawFocusName;

        Focus(String rawFocusName) {
            this.rawFocusName = rawFocusName;
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
    private NetworkTableEntry m_focus;
    private NetworkTableEntry m_algorithm;
    private NetworkTableEntry m_values;
    private NetworkTableEntry m_found;
    private Logger logger;
    private Error m_lastError = Error.OK;

    public VisionMaster() {
        logger = LogManager.getLogger(getClass());
        m_visionTable = NetworkTableInstance.getDefault().getTable("vision");
        m_algorithm = m_visionTable.getEntry("algorithm");
        m_focus = m_visionTable.getEntry("focus");
        m_values = m_visionTable.getEntry("output");
        m_found = m_visionTable.getEntry("found");
    }

    public void setCurrentAlgorithm(Algorithm algo) {
        m_algorithm.setString(algo.getRawName());
    }

    public void setCurrentFocus(Focus focus) {
        m_focus.setString(focus.rawFocusName);
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

        if (input == null) return new StandardVisionData[]
                {new StandardVisionData(new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN})};

        StandardVisionData[] ret = new StandardVisionData[input.length / 4];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new StandardVisionData(input[4 * i], input[4 * i + 1], input[4 * i + 2], input[4 * i + 3]);
        }
        return ret;
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

    public double getPlaneryDistance() {
        return getPlaneryDistance(0);
    }

    public double getRelativeAngle() {
        return getRelativeAngle(0);
    }

    public double getAngle() {
        return getAngle(0);
    }

    public void getCurrentVisionData(double[] dest) {
        m_values.getDoubleArray(dest);
    }

    public boolean isDataValid() {
        return m_found.getBoolean(false);
    }

    public void update() {
        var current = getStandardizedData()[0];
        SmartDashboard.putString("Vision::focus", m_visionTable.getEntry("focus").getString(""));
        SmartDashboard.putString("Vision::raw data", current.toString());
        SmartDashboard.putNumber("Vision::planery distance", current.getPlaneryDistance());
    }
}
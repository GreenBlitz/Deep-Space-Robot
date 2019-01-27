package frc.utils.ctre;

/**
 * This class creates a standardized way to do robot drive with our 4 Talon setup.
 * A lot of this class is made obsolete by the RobotDrive() class in WPIlib. However, the enums here are still very useful.
 * setRightLeftMotorOutput() sets the motor outputs based on the limit and the output scale.
 *
 * There is a helpful TalonID enum in place to easily differentiate the Talons from inside and outside this class.
 *
 * @see com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
 * @see edu.wpi.first.wpilibj.drive.RobotDriveBase
 * @see org.usfirst.frc.team4590.robot.RobotMap
 */
public class CANRobotDrive {

    private SmartTalon m_frontLeft,
                       m_rearLeft,
                       m_frontRight,
                       m_rearRight;

    private double mOutputScale = 1;
    private double mPowerLimit = 1;
    private int m_frontLeftInverted = 1, m_rearLeftInverted = 1, m_frontRightInverted = 1, m_rearRightInverted = 1;

    /**
     * This constructor sets the talons to the according talon ports from the RobotMap file.
     */
    public CANRobotDrive(int frontLeft, int rearLeft, int frontRight, int rearRight) {
        m_frontLeft = new SmartTalon(frontLeft);
        m_rearLeft = new SmartTalon(rearLeft);
        m_frontRight = new SmartTalon(frontRight);
        m_rearRight = new SmartTalon(rearRight);
    }

    public CANRobotDrive(SmartTalon frontLeft, SmartTalon rearLeft, SmartTalon frontRight, SmartTalon rearRight) {
        m_frontLeft = frontLeft;
        m_rearLeft = rearLeft;
        m_frontRight = frontRight;
        m_rearRight = rearRight;
    }

    /**
     * This is a helpful TalonID enum for us to easily differentiate the Talons from inside and outside this class.
     */
    public enum TalonID {
        FRONT_LEFT, REAR_LEFT, FRONT_RIGHT, REAR_RIGHT
    }

    /**
     * This function returns a talon based on the TalonID it receives.
     *
     * @param id The id of the talon based on the TalonID enum we want to get
     * @return The talon based on the TalonID given or null (if the TalonID doesn't match any of the talons
     */
    public SmartTalon getTalon(TalonID id) {
        switch (id) {
            case FRONT_LEFT:
                return m_frontLeft;
            case REAR_LEFT:
                return m_rearLeft;
            case FRONT_RIGHT:
                return m_frontRight;
            case REAR_RIGHT:
                return m_rearRight;
            default:
                return null;
        }
    }

    /**
     * This function inverts the motor it was given the TalonID for.
     *
     * @param id The id of the talon based on the TalonID enum we want to invert
     * @param inverted A boolean that says whether we should invert the direction or not
     */
    public void setInvetedMotor(TalonID id, boolean inverted) {
        switch (id) {
            case FRONT_LEFT:
                m_frontLeftInverted = inverted ? -1 : 1;
                break;
            case REAR_LEFT:
                m_rearLeftInverted = inverted ? -1 : 1;
                break;
            case FRONT_RIGHT:
                m_frontRightInverted = inverted ? -1 : 1;
                break;
            case REAR_RIGHT:
                m_rearRightInverted = inverted ? -1 : 1;
                break;
        }
    }

    /**
     * This function gives all outputs to the talon an output scale. (mOutputScale)
     *
     * @param maxOutput A output scale that will scale all outputs given to the talon.
     */
    public void setOutputScale(double maxOutput) {
        mOutputScale = maxOutput;
    }

    /**
     * This function sets a power limit for the talon. (mPowerLimit)
     *
     * @param powerLimit A power limit that will be applied to the talon.
     */
    public void setPowerLimit(double powerLimit) {
        mPowerLimit = powerLimit;
    }

    /**
     * This function is a regular arcadeDrive() function, only using the output scale and power limit we included.
     *
     * @param moveValue The output value of the move portion of the arcade drive.
     * @param rotateValue The output value of the rotation portion of the arcade drive.
     */
    public void arcadeDrive(double moveValue, double rotateValue) {
        double tmp = moveValue;
        moveValue = rotateValue;
        rotateValue = -tmp;

        double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue = limit(-moveValue);
        rotateValue = limit(rotateValue);

        moveValue = Math.copySign(moveValue * moveValue, moveValue);
        rotateValue = Math.copySign(rotateValue * rotateValue, rotateValue);

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
    }

    /**
     * This function is a regular tankDrive() function, only using the output scale and power limit we included.
     *
     * @param leftValue The output value given to the left motors
     * @param rightValue The output value given to the right motors
     */
    public void tankDrive(double leftValue, double rightValue) {
        leftValue = limit(-leftValue);
        rightValue = limit(-rightValue);

        leftValue = Math.copySign(leftValue * leftValue, leftValue);
        rightValue = Math.copySign(rightValue * rightValue, rightValue);

        setLeftRightMotorOutputs(leftValue, rightValue);
    }

    /**
     * This function makes sure that the power doesn't go over or under the power limit.
     *
     * @param value A power value given to the motors
     * @return A power value based off of the power limit
     */
    private double limit(double value) {
        if (value > 0)
            return Math.min(value, mPowerLimit);
        return Math.max(value, -mPowerLimit);
    }

    /**
     * This function multiplies the motor outputs by the output scale, making sure they don't go over the power limit still.
     *
     * @param leftOutput The original output for the left motors
     * @param rightOutput The original output for the right motors
     */
    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        m_frontLeft.set(m_frontLeftInverted * limit(leftOutput) * mOutputScale);
        m_rearLeft.set(m_rearLeftInverted * limit(leftOutput) * mOutputScale);
        m_frontRight.set(m_frontRightInverted * limit(rightOutput) * mOutputScale);
        m_rearRight.set(m_rearRightInverted * limit(rightOutput) * mOutputScale);
    }
}
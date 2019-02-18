package edu.greenblitz.utils.drive;

import edu.wpi.first.wpilibj.SpeedController;

import java.util.logging.Logger;

/**
 * This class creates a standardized way to do robot drive with our 4 Talon setup.
 * A lot of this class is made obsolete by the RobotDrive() class in WPIlib. However, the enums here are still very useful.
 * setRightLeftMotorOutput() sets the motor outputs based on the limit and the output scale.
 * <p>
 * There is a helpful MotorID enum in place to easily differentiate the Talons from inside and outside this class.
 *
 * @see com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
 * @see edu.wpi.first.wpilibj.drive.RobotDriveBase
 */
public class RobotDrive<C extends SpeedController> {

    private Logger logger;

    private C m_frontLeft,
            m_middleLeft,
            m_rearLeft,
            m_frontRight,
            m_middleRight,
            m_rearRight;

    private double m_outputScale = 1;
    private double m_powerLimit = 1;
    private int m_frontLeftInverted = 1,
            m_middleLeftInverted = 1,
            m_rearLeftInverted = 1,
            m_frontRightInverted = 1,
            m_middleRightInverted = 1,
            m_rearRightInverted = 1;

    public RobotDrive(Logger activityLogger, C frontLeft, C middleLeft, C rearLeft, C frontRight, C middleRight, C rearRight) {
        m_frontLeft = frontLeft;
        m_middleLeft = middleLeft;
        m_rearLeft = rearLeft;
        m_frontRight = frontRight;
        m_middleRight = middleRight;
        m_rearRight = rearRight;
        logger = activityLogger;

        if (logger != null) {
            logger.info("RobotDrive is instantiated");
        }
    }

    public RobotDrive(C frontLeft, C middleLeft, C rearLeft, C frontRight, C middleRight, C rearRight) {
        this(null, frontLeft, middleLeft, rearLeft, frontRight, middleRight, rearRight);
    }


    /**
     * This is a helpful MotorID enum for us to easily differentiate the Talons from inside and outside this class.
     */
    public enum MotorID {
        FRONT_LEFT, MIDDLE_LEFT, REAR_LEFT, FRONT_RIGHT, MIDDLE_RIGHT, REAR_RIGHT
    }

    /**
     * This function returns a talon based on the MotorID it receives.
     *
     * @param id The id of the talon based on the MotorID enum we want to get
     * @return The talon based on the MotorID given or null (if the MotorID doesn't match any of the talons
     */
    public C getMotor(MotorID id) {
        switch (id) {
            case FRONT_LEFT:
                return m_frontLeft;
            case MIDDLE_LEFT:
                return m_middleLeft;
            case REAR_LEFT:
                return m_rearLeft;
            case FRONT_RIGHT:
                return m_frontRight;
            case MIDDLE_RIGHT:
                return m_middleRight;
            case REAR_RIGHT:
                return m_rearRight;
            default:
                throw new IllegalArgumentException("roses are red. violets are blue. I don't know this enum (" + id + "), what should I do?");
        }
    }

    /**
     * This function inverts the motor it was given the MotorID for.
     *
     * @param id       The id of the talon based on the MotorID enum we want to invert
     * @param inverted A boolean that says whether we should invert the direction or not
     */
    public void setInvetedMotor(MotorID id, boolean inverted) {
        switch (id) {
            case FRONT_LEFT:
                m_frontLeftInverted = inverted ? -1 : 1;
                break;
            case MIDDLE_LEFT:
                m_middleLeftInverted = inverted ? -1 : 1;
                break;
            case REAR_LEFT:
                m_rearLeftInverted = inverted ? -1 : 1;
                break;
            case FRONT_RIGHT:
                m_frontRightInverted = inverted ? -1 : 1;
                break;
            case MIDDLE_RIGHT:
                m_middleRightInverted = inverted ? -1 : 1;
                break;
            case REAR_RIGHT:
                m_rearRightInverted = inverted ? -1 : 1;
                break;
        }
    }

    /**
     * This function gives all outputs to the talon an output scale. (m_outputScale)
     *
     * @param maxOutput A output scale that will scale all outputs given to the talon.
     */
    public void setOutputScale(double maxOutput) {
        m_outputScale = maxOutput;
    }

    /**
     * This function sets a power limit for the talon. (m_powerLimit)
     *
     * @param powerLimit A power limit that will be applied to the talon.
     */
    public void setPowerLimit(double powerLimit) {
        m_powerLimit = powerLimit;
    }

    /**
     * This function is a regular arcadeDrive() function, only using the output scale and power limit we included.
     *
     * @param rotate   The output value of the move portion of the arcade drive.
     * @param move The output value of the rotation portion of the arcade drive.
     */
    public void arcadeDrive(double move, double rotate, boolean square) {
        logFinest("arcadeDrive (%b): move-%f, rotate-%f", square, move, rotate);

        double leftMotorSpeed;
        double rightMotorSpeed;

        rotate = limit(rotate);
        move = limit(move);

        if (square) {
            rotate = Math.copySign(rotate * rotate, rotate);
            move = Math.copySign(move * move, move);
        }

        if (rotate > 0.0) {
            if (move > 0.0) {
                leftMotorSpeed = rotate - move;
                rightMotorSpeed = Math.max(rotate, move);
            } else {
                leftMotorSpeed = Math.max(rotate, -move);
                rightMotorSpeed = rotate + move;
            }
        } else {
            if (move > 0.0) {
                leftMotorSpeed = -Math.max(-rotate, move);
                rightMotorSpeed = rotate + move;
            } else {
                leftMotorSpeed = rotate - move;
                rightMotorSpeed = -Math.max(-rotate, -move);
            }
        }

        setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
    }

    /**
     * This function is a regular tankDrive() function, only using the output scale and power limit we included.
     *
     * @param left  The output value given to the left motors
     * @param right The output value given to the right motors
     */
    public void tankDrive(double left, double right, boolean square) {
        logFinest("tankDrive (%b): left-%f, right-%f", square, left, right);

        if (square) {
            left = Math.copySign(left * left, left);
            right = Math.copySign(right * right, right);
        }

        left = limit(left);
        right = limit(right);

        setLeftRightMotorOutputs(left, right);
    }

    public void arcadeDrive(double move, double rotate) {
        arcadeDrive(move, rotate, false);
    }

    public void tankDrive(double left, double right) {
        tankDrive(left, right, false);
    }

    /**
     * This function makes sure that the power doesn't go over or under the power limit.
     *
     * @param value A power value given to the motors
     * @return A power value based off of the power limit
     */
    private double limit(double value) {
        if (value > 0)
            return Math.min(value, m_powerLimit);
        return Math.max(value, -m_powerLimit);
    }

    /**
     * This function multiplies the motor outputs by the output scale, making sure they don't go over the power limit still.
     *
     * @param leftOutput  The original output for the left motors
     * @param rightOutput The original output for the right motors
     */
    private void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        var left = limit(leftOutput) * m_outputScale;
        var right = limit(rightOutput) * m_outputScale;

        var fl = m_frontLeftInverted * left; // front left
        var ml = m_middleLeftInverted * left; // mid left
        var rl = m_rearLeftInverted * left; // rear left

        var fr = m_frontRightInverted * right; // front right
        var mr = m_middleRightInverted * right; // mid right
        var rr = m_rearRightInverted * right; // rear right

        m_frontLeft.set(fl);
        m_middleLeft.set(ml);
        m_rearLeft.set(rl);
        m_frontRight.set(fr);
        m_middleRight.set(mr);
        m_rearRight.set(rr);

        logFinest(
                "setting motors: left - [%f, %f, %f], right - [%f, %f, %f]",
                fl, ml, rl, fr, mr, rr);
    }

    private void logFinest(String msg) {
        if (logger != null) {
            logger.fine(msg);
        }
    }

    private void logFinest(String msg, Object... args) {
        logFinest(String.format(msg, args));
    }
}
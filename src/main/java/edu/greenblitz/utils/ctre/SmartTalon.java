package edu.greenblitz.utils.ctre;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * This class uses a device number in order create a TalonSRX object, while optimizing a few things with the set() function.
 * It should be noted that this class is not very useful if WPI_TalonSRX is used.
 * The class extends the TalonSRX object {@link TalonSRX}
 * getLastValue(), wasSet() and newIteration() aren't very useful and thus aren't used in the code.
 *
 * @see edu.wpi.first.wpilibj.Talon
 * @see com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
 */

public class SmartTalon extends WPI_TalonSRX {

	private boolean wasSet;

	/**
	 * The constructor uses a talon device number in order to initialize the TalonSRX object.
	 *
	 * @param deviceNumber The device number of the TalonSRX that is being constructed.
	 */
	public SmartTalon(int deviceNumber) {
		super(deviceNumber);
	}
	
	private double m_lastValue = 0;

	/**
	 * This function uses a power input in order to use the set() function of the TalonSRX, while setting it to percent output.
	 * This also sets m_lastValue to power and wasSet to true.
	 *
	 * @param power The percentage power value the robot will work at.
	 */
	public void set(double power) {
		m_lastValue = power;
		set(ControlMode.PercentOutput, power);
		wasSet = true;
	}

	/**
	 * This function returns the last value given to the TalonSRX.
	 *
	 * @return m_lastValue Returns the last value given to the motor.
	 */
	public double getLastValue(){ 
		return m_lastValue;
	}

	/**
	 * This function returns whether the TalonSRX was set already.
	 *
	 * @return wasSet A boolean that is equal to whether the talon was set already or not.
	 */
	public boolean wasSet() {
		return wasSet;
	}

	/**
	 * This function sets wasSet to false.
	 */
	public void newIterration() {
		wasSet = false;
	}
}
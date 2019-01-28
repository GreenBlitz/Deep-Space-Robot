package frc.utils.ctre;

/**
 * This class has many functions that make using an encoder much simpler.
 * The class uses the encoders prebuilt into the SmartEncoderBase for all calculations.
 *
 * @see edu.wpi.first.wpilibj.Encoder
 * @see SmartEncoderBase
 */
public class SmartEncoder {
	private final SmartEncoderBase m_motorController;
	private final double m_ticksPerMeter;

	/**
	 * This constructor receives a SmartEncoderBase and the ticks per meter of the Talon
	 * It also checks to see if the ticks per meter are valid as well.
	 * @param motorController A SmartEncoderBase object which is used of it's encoder. (m_motorController)
	 * @param ticksPerMeter A final double of the ticks per meter the talon feels per meter of movement.
	 */
	public SmartEncoder(SmartEncoderBase motorController, double ticksPerMeter) {
		if (ticksPerMeter == +0.0 || !Double.isFinite(ticksPerMeter))
			throw new IllegalArgumentException("invalid ticks per meter value '" + ticksPerMeter + "'");

		m_motorController = motorController;
		m_ticksPerMeter = ticksPerMeter;
	}

	/**
	 * This function returns the amount of ticks that have been felt by encoder since it was last reset.
	 *
	 * @return The amount of ticks felt by the talon.
	 */
	public int getTicks() {
		return m_motorController.getQuadraturePosition();
	}

	public double getRawSpeed() {
	    return m_motorController.getQuadratureVelocity();
    }

	/**
	 * This function returns the amount of meters that the encoder has felt, by dividing tick by the ticks per meter value.
	 *
	 * @return The ticks felt by the talon divided by the ticks it feels per meter.
	 */
	public double getDistance() {
		return getTicks() / m_ticksPerMeter;
	}

	/**
	 * This function returns the velocity felt by the encoder divided by the ticks per meter.
	 *
	 * @return The velocity felt by the talon divided by the ticks per meter.
	 */
	public double getSpeed() {
		return getRawSpeed() / m_ticksPerMeter;
	}

	/**
	 * This function resets the encoder
	 *
	 * @return Error code if the encoder could not be reset, otherwise resets the encoder.
	 */
    public void reset() {
		m_motorController.reset();
	}
}
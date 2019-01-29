package frc.motion.base.abstraction;

public interface IEncoder {
    /**
     *
     * @return raw encoder ticks
     */
    int getTicks();

    /**
     *
     * @return measured distance (ticks modified by ticks per meter)
     */
    double getDistance();

    /**
     *
     * @return raw encoder tick rate
     */
    double getTickRate();

    /**
     *
     * @return measured velocity (tick rate modified by ticks per meter
     */
    double getVelocity();

    /**
     * Resets the encoder
     */
    void reset();

    double getTicksPerMeter();
}

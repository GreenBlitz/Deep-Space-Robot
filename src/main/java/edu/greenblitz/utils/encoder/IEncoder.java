package edu.greenblitz.utils.encoder;

public interface IEncoder {

    /**
     * Resets the encoder. if the motor hasn't moved after this was called, getRawTicks is expected to return 0.
     * Calling this while the motor is moving might be problematic.
     */
    void reset();

    /**
     *
     * @return raw amount of ticks so far
     */
    int getRawTicks();

    /**
     *
     * @return ticks per second
     */
    double getTickRate();

    double getNormalizeConst();

    void setNormalizeConst(double value);

    default double getNormalizedTicks() {
        return getRawTicks() * invert() / getNormalizeConst();
    }

    default double getNormalziedVelocity() {
        return getTickRate() * invert() / getNormalizeConst();
    }

    void invert(boolean inverted);

    boolean isInverted();

    private int invert() {
        return isInverted() ? -1 : 1;
    }
}
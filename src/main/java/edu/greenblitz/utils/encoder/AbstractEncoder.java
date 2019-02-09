package edu.greenblitz.utils.encoder;

public abstract class AbstractEncoder implements IEncoder {

    private double m_normalizeConst;
    private boolean m_inverted;

    /**
     * This constructor receives the normalize constant of the motor controller.
     * It also checks to see if the constant valid as well.
     *
     * @param normalizeConst A double of the ticks per meter of movement.
     */
    public AbstractEncoder(double normalizeConst) {
        if (normalizeConst == +0.0 || !Double.isFinite(normalizeConst))
            throw new IllegalArgumentException("invalid ticks per meter value '" + normalizeConst + "'");

        m_normalizeConst = normalizeConst;
    }

    @Override
    public double getNormalizeConst() {
        return m_normalizeConst;
    }

    @Override
    public void setNormalizeConst(double value) {
        m_normalizeConst = value;
    }

    @Override
    public void invert(boolean inverted) {
        m_inverted = inverted;
    }

    @Override
    public boolean isInverted() {
        return m_inverted;
    }
}

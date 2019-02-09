package edu.greenblitz.utils.encoder;

import edu.wpi.first.wpilibj.Encoder;

public class RoborioEncoder extends AbstractEncoder {
    private Encoder m_encoder;

    public RoborioEncoder(double normalizeConst, Encoder encoder) {
        super(normalizeConst);
        m_encoder = encoder;
    }

    public RoborioEncoder(double normalizeCost, int channelA, int channelB) {
        this(normalizeCost, new Encoder(channelA, channelB));
    }

    @Override
    public void reset() {
        m_encoder.reset();
    }

    @Override
    public int getRawTicks() {
        return m_encoder.get();
    }

    @Override
    public double getTickRate() {
        return m_encoder.getRate();
    }
}

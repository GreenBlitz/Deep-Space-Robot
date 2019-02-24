package edu.greenblitz.utils.encoder;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

public class SparkEncoder extends AbstractEncoder {
    private static final double TICK_SIZE = 1.0 / 42;

    private CANEncoder m_sparkEncoder;
    private int m_nullPosition;

    public SparkEncoder(double normalizeConst, CANSparkMax sparkEncoder) {
        super(normalizeConst);
        m_sparkEncoder = sparkEncoder.getEncoder();
        m_nullPosition = 0;
    }

    @Override
    public void reset() {
        m_nullPosition = get();
    }

    private int get() {
        return (int) Math.round(m_sparkEncoder.getPosition() / TICK_SIZE);
    }

    @Override
    public int getRawTicks() {
        return get() - m_nullPosition;
    }

    @Override
    public double getTickRate() {
        return m_sparkEncoder.getVelocity();
    }
}

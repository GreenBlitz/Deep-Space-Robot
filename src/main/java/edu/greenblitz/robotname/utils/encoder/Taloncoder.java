package edu.greenblitz.robotname.utils.encoder;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Taloncoder extends AbstractEncoder {
    private SensorCollection m_talon;

    public Taloncoder(double normalizeConst, TalonSRX talon) {
        super(normalizeConst);
        m_talon = talon.getSensorCollection();
    }

    @Override
    public void reset() {
        m_talon.setQuadraturePosition(0, 100);
    }

    @Override
    public int getRawTicks() {
        return m_talon.getQuadraturePosition();
    }

    @Override
    public double getTickRate() {
        return m_talon.getQuadratureVelocity() * 10;
    }
}

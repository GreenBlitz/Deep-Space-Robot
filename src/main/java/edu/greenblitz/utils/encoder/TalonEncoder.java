package edu.greenblitz.utils.encoder;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.robotname.data.GearDependentDouble;

public class TalonEncoder extends AbstractEncoder {
    private TalonSRX m_talon;

    public TalonEncoder(double normalizeConst, TalonSRX talon) {
        super(normalizeConst);
        m_talon = talon;
    }

    @Override
    public void reset() {
        m_talon.setSelectedSensorPosition(0);
    }

    @Override
    public int getRawTicks() {
        return m_talon.getSelectedSensorPosition();
    }

    @Override
    public double getTickRate() {
        return m_talon.getSelectedSensorVelocity() * 10;
    }

}

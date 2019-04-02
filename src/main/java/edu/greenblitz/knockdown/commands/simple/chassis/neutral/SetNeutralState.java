package edu.greenblitz.knockdown.commands.simple.chassis.neutral;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.knockdown.commands.simple.chassis.ChassisBaseCommand;

public class SetNeutralState extends ChassisBaseCommand {

    private CANSparkMax.IdleMode m_neutralState;

    public SetNeutralState(CANSparkMax.IdleMode neutralState) {
        m_neutralState = neutralState;
    }

    @Override
    protected void atInit() {
        system.setNeutralState(m_neutralState);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

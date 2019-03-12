package edu.greenblitz.robotname.commands.simple.chassis.neutral;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;

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

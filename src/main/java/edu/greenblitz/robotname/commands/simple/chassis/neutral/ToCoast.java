package edu.greenblitz.robotname.commands.simple.chassis.neutral;

import com.revrobotics.CANSparkMax;

public class ToCoast extends SetNeutralState {
    public ToCoast() {
        super(CANSparkMax.IdleMode.kCoast);
    }
}

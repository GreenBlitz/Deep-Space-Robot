package edu.greenblitz.knockdown.commands.simple.chassis.neutral;

import com.revrobotics.CANSparkMax;

public class ToBrake extends SetNeutralState {
    public ToBrake() {
        super(CANSparkMax.IdleMode.kBrake);
    }
}

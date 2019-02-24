package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.simple.kicker.CloseKicker;
import edu.greenblitz.robotname.commands.simple.kicker.KickCargo;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class KickAndReturn extends CommandGroup {
    public KickAndReturn() {
        addSequential(new KickCargo());
        addSequential(new CloseKicker());
    }
}
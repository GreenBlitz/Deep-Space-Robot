package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.CloseKicker;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureKickerClosed extends CommandGroup {
    public EnsureKickerClosed() {
        addSequential(new CloseKicker());
    }
}

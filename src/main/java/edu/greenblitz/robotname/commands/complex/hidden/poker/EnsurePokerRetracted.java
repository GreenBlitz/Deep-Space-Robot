package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.simple.poker.ClosePoker;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsurePokerRetracted extends CommandGroup {
    public EnsurePokerRetracted() {
        addSequential(new EnsureKickerClosed());
        addSequential(new ClosePoker());
    }
}

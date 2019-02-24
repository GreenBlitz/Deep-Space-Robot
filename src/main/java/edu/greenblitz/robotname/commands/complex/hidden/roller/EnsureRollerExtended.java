package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureRollerExtended extends CommandGroup {
    public EnsureRollerExtended() {
        addSequential(new EnsurePokerRetracted());
        addSequential(new ExtendRoller());
    }
}

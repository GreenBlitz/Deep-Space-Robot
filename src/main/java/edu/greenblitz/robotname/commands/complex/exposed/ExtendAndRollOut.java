package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RollOut;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExtendAndRollOut extends CommandGroup {
    public ExtendAndRollOut() {
        addSequential(new EnsurePokerRetracted());
        addSequential(new RollOut());
        addParallel(new ExtendRoller());
    }
}

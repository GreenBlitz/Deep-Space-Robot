package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RollIn;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExtendAndRollIn extends CommandGroup {
    public ExtendAndRollIn() {
        addSequential(new EnsurePokerRetracted());
        addSequential(new RollIn());
        addParallel(new ExtendRoller());
    }
}

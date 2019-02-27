package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectHatch extends CommandGroup {
    public CollectHatch() {
        addSequential(new ExtendPoker());
        //addSequential(new );
    }
}

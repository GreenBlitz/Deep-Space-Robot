package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.simple.poker.PushFrontPoker;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectHatch extends CommandGroup {
    public CollectHatch() {
        addSequential(new PushFrontPoker());
        //addSequential(new );
    }
}

package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollOut;
import edu.greenblitz.utils.command.chain.CommandChain;

public class CollectBall extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new ExtendAndRollIn());
        addSequential(new EnsureRollerRetracted());
    }
}

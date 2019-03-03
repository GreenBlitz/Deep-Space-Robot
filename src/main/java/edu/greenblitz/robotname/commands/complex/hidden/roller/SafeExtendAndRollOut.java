package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.roller.RollOut;
import edu.greenblitz.utils.command.chain.CommandChain;

public class SafeExtendAndRollOut extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new EnsureRollerExtended());
        addSequential(new RollOut(1000));
    }
}

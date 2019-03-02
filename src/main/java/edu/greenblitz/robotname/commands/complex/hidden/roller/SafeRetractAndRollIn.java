package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.roller.RollIn;
import edu.greenblitz.utils.command.chain.CommandChain;

public class SafeRetractAndRollIn extends CommandChain {
    @Override
    protected void initChain() {
        addParallel(new EnsureRollerRetracted(), new RollIn());
    }
}

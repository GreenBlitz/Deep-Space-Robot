package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.roller.StopRolling;
import edu.greenblitz.utils.command.chain.CommandChain;

public class SafeRetractAndStop extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new StopRolling());
        addSequential(new EnsureRollerRetracted());
    }
}

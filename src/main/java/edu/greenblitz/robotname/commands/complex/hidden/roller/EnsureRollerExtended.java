package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.utils.command.chain.CommandChain;

public class EnsureRollerExtended extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new EnsurePokerRetracted());
        addSequential(new ExtendRoller());
    }
}

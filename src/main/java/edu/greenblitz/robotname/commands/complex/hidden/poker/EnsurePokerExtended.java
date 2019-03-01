package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class EnsurePokerExtended extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new EnsurePokerRetracted());
        addSequential(new ExtendPoker());
    }
}
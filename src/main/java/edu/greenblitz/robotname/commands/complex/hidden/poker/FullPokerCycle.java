package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.simple.poker.ExtendAndRelease;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.utils.command.chain.CommandChain;

//TODO: Doc initial and end conditions. (Ensure safety)
public class FullPokerCycle extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new ExtendAndRelease());
        addSequential(new RetractAndHold());
    }
}

package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.utils.command.chain.CommandChain;

public class ButtonReleaseAndHold extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new ReleaseHatch());
    }

    protected void end() {
        new HoldHatch().start();
    }
}

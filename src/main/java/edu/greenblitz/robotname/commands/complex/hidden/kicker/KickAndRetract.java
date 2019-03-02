package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.utils.command.chain.CommandChain;

public class KickAndRetract extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new Kick());
        addSequential(new Unkick());
    }
}

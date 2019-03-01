package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.NullCommand;

public class EnsureRollerExtended extends CommandChain {
    @Override
    protected void initChain() {
        if(Roller.getInstance().isExtended()) {
            addSequential(new NullCommand());
            return;
        }
        if(Poker.getInstance().isExtended())
            addSequential(new EnsurePokerRetracted());
        addSequential(new ExtendRoller());
    }
}

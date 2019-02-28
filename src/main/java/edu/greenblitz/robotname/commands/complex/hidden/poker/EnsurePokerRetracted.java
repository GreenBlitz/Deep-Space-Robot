package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.simple.poker.ClosePoker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class EnsurePokerRetracted extends CommandChain {
    public EnsurePokerRetracted() {
        super(new EnsureKickerClosed());
        addSequential(new ClosePoker());
    }
}

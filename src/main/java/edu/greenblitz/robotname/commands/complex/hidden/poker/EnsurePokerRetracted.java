package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.simple.poker.ClosePoker;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.NullCommand;

public class EnsurePokerRetracted extends CommandChain {
    @Override
    protected void initChain() {
        if(Poker.getInstance().isRetracted()){
            addSequential(new NullCommand());
            return;
        }
        if(Kicker.getInstance().isOpen())
            addSequential(new EnsureKickerClosed());
        addSequential(new ClosePoker());
    }
}

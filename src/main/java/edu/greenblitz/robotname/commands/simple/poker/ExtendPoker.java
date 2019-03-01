package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ExtendPoker extends PokerBaseCommand {
    private static final long POKER_EXTEND_TIMEOUT = 1000;

    public ExtendPoker() {
        super(POKER_EXTEND_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.extend(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.POKING;
    }

}

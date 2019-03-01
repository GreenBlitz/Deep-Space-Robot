package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class RetractPoker extends PokerBaseCommand {
    private static final long POKER_RETRACT_TIMEOUT = 1000;

    public RetractPoker() {
        super(POKER_RETRACT_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.extend(false);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }
}

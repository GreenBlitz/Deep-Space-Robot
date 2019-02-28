package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class TogglePokerExtender extends TimedSubsystemCommand<Poker> {
    private static final long POKER_TOGGLE_TIMEOUT = 1000;

    public TogglePokerExtender() {
        super(POKER_TOGGLE_TIMEOUT, Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(!system.isExtended());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class ExtendPoker extends TimedSubsystemCommand<Poker> {
    private static final long POKER_EXTEND_TIMEOUT = 1000;

    public ExtendPoker() {
        super(POKER_EXTEND_TIMEOUT, Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(true);
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }
}

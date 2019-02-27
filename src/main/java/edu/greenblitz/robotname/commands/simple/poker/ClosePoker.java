package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class ClosePoker extends TimedSubsystemCommand<Poker> {
    private static final long POKER_CLOSE_TIMEOUT = 1000;

    public ClosePoker() {
        super(Poker.getInstance(), POKER_CLOSE_TIMEOUT);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Kicker.getInstance()};
    }

    @Override
    protected void timedInitialize() {
        system.fullClose();
    }

    @Override
    protected boolean isFinished() {
        return system.isFullyClosed();
    }
}

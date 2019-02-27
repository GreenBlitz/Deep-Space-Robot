package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class ExtendPoker extends TimedSubsystemCommand<Poker> {
    private static final long POKER_EXTEND_TIMEOUT = 1000;

    public ExtendPoker() {
        super(Poker.getInstance(), POKER_EXTEND_TIMEOUT);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Kicker.getInstance()};
    }

    @Override
    protected void timedInitialize() {
        system.extend(true);
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }
}

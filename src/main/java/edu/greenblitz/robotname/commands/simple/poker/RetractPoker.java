package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class RetractPoker extends TimedSubsystemCommand<Poker> {
    private static final long POKER_RETRACT_TIMEOUT = 1000;

    public RetractPoker() {
        super(Poker.getInstance(), POKER_RETRACT_TIMEOUT);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Kicker.getInstance()};
    }

    @Override
    protected void timedInitialize() {
        system.extend(false);
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }
}

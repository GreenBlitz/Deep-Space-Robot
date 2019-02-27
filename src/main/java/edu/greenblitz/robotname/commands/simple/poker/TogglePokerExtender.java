package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TogglePokerExtender extends TimedSubsystemCommand<Poker> {
    private static final long POKER_TOGGLE_TIMEOUT = 1000;

    public TogglePokerExtender() {
        super(Poker.getInstance(), POKER_TOGGLE_TIMEOUT);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Kicker.getInstance()};
    }

    @Override
    protected void timedInitialize() {
        system.extend(!system.isExtended());
//        system.extend(system.isExtendsed());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ToggleKicker extends TimedSubsystemCommand<Kicker> {
    private static final long KICKER_TOGGEL_TIMEOUT = 1000;

    public ToggleKicker() {
        super(Kicker.getInstance(), KICKER_TOGGEL_TIMEOUT);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Poker.getInstance(), Roller.getInstance()};
    }

    @Override
    protected void timedInitialize() {
        system.kick(!system.isOpen());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

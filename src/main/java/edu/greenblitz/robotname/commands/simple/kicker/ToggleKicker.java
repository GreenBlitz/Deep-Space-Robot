package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class ToggleKicker extends TimedSubsystemCommand<Kicker> {
    private static final long KICKER_TOGGEL_TIMEOUT = 1000;

    public ToggleKicker() {
        super(KICKER_TOGGEL_TIMEOUT, Kicker.getInstance());
    }

    @Override
    protected void initialize() {
        system.kick(!system.isOpen());
    }
}

package edu.greenblitz.knockdown.commands.simple.kicker;

import static edu.greenblitz.knockdown.subsystems.Kicker.KICKER_STATE_TIMEOUT;

public class ToggleKicker extends KickerBaseCommand {
    public ToggleKicker() {
        super(KICKER_STATE_TIMEOUT);
    }

    @Override
    protected void atInit() {
        system.kick(!system.isOpen());
    }
}

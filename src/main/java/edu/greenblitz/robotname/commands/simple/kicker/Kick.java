package edu.greenblitz.robotname.commands.simple.kicker;

import static edu.greenblitz.robotname.subsystems.Kicker.KICKER_STATE_TIMEOUT;

public class Kick extends KickerBaseCommand {
    public Kick() {
        this(KICKER_STATE_TIMEOUT);
    }

    public Kick(long ms) {
        super(ms);
    }

    @Override
    protected void atInit() {
        system.kick();
    }
}

package edu.greenblitz.knockdown.commands.complex.kicker;

import edu.greenblitz.knockdown.commands.simple.kicker.Kick;
import edu.greenblitz.knockdown.commands.simple.kicker.Unkick;
import edu.greenblitz.knockdown.subsystems.Kicker;
import edu.greenblitz.utils.command.CommandChain;

public class KickAndRetract extends CommandChain {
    public KickAndRetract() {
        this(Kicker.KICKER_STATE_TIMEOUT);
    }

    public KickAndRetract(long timeout) {
        addSequential(new Kick(timeout));
        addSequential(new Unkick(timeout));
    }

    public KickAndRetract(long timeout, String name) {
        super(name);

        addSequential(new Kick(timeout));
        addSequential(new Unkick(timeout));
    }
}

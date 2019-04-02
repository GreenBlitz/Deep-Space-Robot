package edu.greenblitz.knockdown.commands.simple.roller;

import edu.greenblitz.knockdown.subsystems.Roller;
import edu.greenblitz.utils.command.base.TimedSubsystemCommand;

import static edu.greenblitz.knockdown.subsystems.Roller.ROLLER_STATE_TIMEOUT;

public abstract class RollerBaseCommand extends TimedSubsystemCommand<Roller> {

    public RollerBaseCommand() { this(ROLLER_STATE_TIMEOUT); }

    public RollerBaseCommand(long ms) {
        super(ms, Roller.getInstance());
    }

    public RollerBaseCommand(String name, long ms) {
        super(name, ms, Roller.getInstance());
    }
}

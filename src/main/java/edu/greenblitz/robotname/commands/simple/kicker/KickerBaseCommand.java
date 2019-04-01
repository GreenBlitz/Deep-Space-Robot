package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.base.TimedSubsystemCommand;

public abstract class KickerBaseCommand extends TimedSubsystemCommand<Kicker> {
    public KickerBaseCommand(long ms) {
        super(ms, Kicker.getInstance());
    }

    public KickerBaseCommand(String name, long ms) {
        super(name, ms, Kicker.getInstance());
    }

}

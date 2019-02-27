package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ToggleKicker extends SubsystemCommand<Kicker> {
    public ToggleKicker() {
        super(Kicker.getInstance());
    }

    @Override
    protected void initialize() {
        system.kick(!system.isOpen());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

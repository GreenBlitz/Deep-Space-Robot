package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ToggleRoller extends SubsystemCommand<Roller> {
    public ToggleRoller() {
        super(Roller.getInstance());
    }

    @Override
    protected void initialize() {
        system.setExtender(!system.isExtended());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

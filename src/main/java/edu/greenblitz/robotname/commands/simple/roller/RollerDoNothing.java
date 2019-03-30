package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RollerDoNothing extends RollerBaseCommand {

    @Override
    protected void atInit() {
        system.setDefaultCommand(this);
    }

    @Override
    protected RollerState getNextState() {
        return null;
    }
}

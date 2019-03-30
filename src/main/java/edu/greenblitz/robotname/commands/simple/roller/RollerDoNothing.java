package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RollerDoNothing extends RollerBaseCommand {

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected RollerState getNextState() {
        return null;
    }
}

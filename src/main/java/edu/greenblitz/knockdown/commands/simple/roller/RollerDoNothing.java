package edu.greenblitz.knockdown.commands.simple.roller;

public class RollerDoNothing extends RollerBaseCommand {

    @Override
    protected boolean isFinished() {
        return false;
    }

}

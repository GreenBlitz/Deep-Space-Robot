package edu.greenblitz.knockdown.commands.simple.chassis.motion;

import edu.greenblitz.knockdown.Robot;
import edu.greenblitz.utils.command.base.GBCommand;

public class RunAuto extends GBCommand {

    @Override
    protected void atInit() {
        Robot.getInstance().getAutonomous().start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

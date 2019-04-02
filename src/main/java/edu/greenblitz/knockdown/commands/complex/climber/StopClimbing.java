package edu.greenblitz.knockdown.commands.complex.climber;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Climber;
import edu.greenblitz.utils.command.base.GBCommand;

public class StopClimbing extends GBCommand {

    public StopClimbing(){
        requires(Chassis.getInstance());
        requires(Climber.getInstance().getWheels());
        requires(Climber.getInstance().getBig());
        requires(Climber.getInstance().getExtender());
    }


    @Override
    protected boolean isFinished() {
        return true;
    }
}

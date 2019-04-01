package edu.greenblitz.robotname.commands.complex.climber;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Climber;
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

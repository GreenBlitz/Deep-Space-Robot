package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.roller.RollWithTriggers;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class StopClimbing extends GBCommand {

    public StopClimbing(){
        requires(Chassis.getInstance());
        requires(Climber.getInstance().getWheels());
        requires(Climber.getInstance().getBig());
        requires(Climber.getInstance().getExtender());
    }


    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void atEnd(){
        new RollWithTriggers(OI.getSideJoystick()).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

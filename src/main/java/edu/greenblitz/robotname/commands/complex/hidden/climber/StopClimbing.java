package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Set;

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
    protected boolean isFinished() {
        return true;
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return Set.of(Chassis.getInstance(), Climber.getInstance().getBig(), Climber.getInstance().getExtender(), Climber.getInstance().getWheels());
    }
}

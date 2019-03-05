package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.commands.simple.chassis.StopChassis;
import edu.greenblitz.robotname.commands.simple.climber.StopClimbDrive;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.chain.CommandChain;

public class StopSideClimberControl extends CommandChain {
    public StopSideClimberControl() {
        requires(Chassis.getInstance());
        requires(Climber.getInstance().getWheels());
    }

    @Override
    protected void initChain() {
        addParallel(new StopChassis(), new StopClimbDrive());
    }
}

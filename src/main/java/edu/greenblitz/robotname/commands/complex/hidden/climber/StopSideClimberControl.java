package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.commands.simple.chassis.StopChassis;
import edu.greenblitz.robotname.commands.simple.climber.StopClimbDrive;
import edu.greenblitz.utils.command.CommandChain;

public class StopSideClimberControl extends CommandChain {
    public StopSideClimberControl() {
        addParallel(new StopChassis(), new StopClimbDrive());
    }
}

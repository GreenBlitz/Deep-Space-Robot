package edu.greenblitz.knockdown.commands.complex.climber;

import edu.greenblitz.knockdown.commands.simple.chassis.StopChassis;
import edu.greenblitz.knockdown.commands.simple.climber.StopClimbDrive;
import edu.greenblitz.utils.command.CommandChain;

public class StopSideClimberControl extends CommandChain {
    public StopSideClimberControl() {
        addParallel(new StopChassis(), new StopClimbDrive());
    }
}

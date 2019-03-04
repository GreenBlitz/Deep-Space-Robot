package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.utils.command.chain.CommandChain;

public class StopSideClimberControl extends CommandChain {
    @Override
    protected void initChain() {
//        addParallel(new StopChassis(), new StopClimbDrive());
    }
}

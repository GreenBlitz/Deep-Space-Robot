package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.simple.climber.ClimbByTriggers;
import edu.greenblitz.robotname.commands.simple.climber.ClimberDriveByJoystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ControlClimber extends CommandGroup {
    public ControlClimber() {
        addSequential(new ClimbByTriggers());
        addParallel(new ClimberDriveByJoystick());
    }
}

package edu.greenblitz.robotname.commands.commandGroups;

import edu.greenblitz.robotname.commands.climber.ClimbByTriggers;
import edu.greenblitz.robotname.commands.climber.ClimberDriveByJoystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ControlClimber extends CommandGroup {
    public ControlClimber() {
        addSequential(new ClimbByTriggers());
        addParallel(new ClimberDriveByJoystick());
    }
}

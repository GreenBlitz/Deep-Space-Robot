package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.RobotMap.Elevator.ElevatorLevel;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

//TODO: Motion Profiling.
public class SetElevatorLevel extends Command {

    public SetElevatorLevel(ElevatorLevel level) {
        requires(Elevator.getInstance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
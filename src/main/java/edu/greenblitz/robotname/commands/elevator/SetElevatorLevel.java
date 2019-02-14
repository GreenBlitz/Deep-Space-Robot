package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.RobotMap.Elevator.ElevatorLevel;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.command.Command;

//TODO: Motion Profiling.
public class SetElevatorLevel extends SubsystemCommand<Elevator> {

    public SetElevatorLevel(ElevatorLevel level) {
        super(Elevator.getInstance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
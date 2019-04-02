package edu.greenblitz.knockdown.commands.complex.elevator;

import edu.greenblitz.knockdown.commands.simple.elevator.MoveElevatorByExternalPID;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class MoveElevatorByLevel extends ConditionalCommand {
    private Elevator.Level m_level;

    public MoveElevatorByLevel(Elevator.Level height, double lowerTol, double higherTol) {
        super(
                new MoveElevatorByExternalPID(height, lowerTol, higherTol),
                new MoveElevatorByExternalPID(height, higherTol, lowerTol)
        );

        m_level = height;
        setName("Move elevator to " + height);
    }

    public MoveElevatorByLevel(Elevator.Level level){
        this(level, Elevator.LOWER_TOLERANCE, Elevator.HIGHER_TOLERANCE);
    }

    @Override
    protected boolean condition() {
        return Elevator.getInstance().getHeight() > m_level.heightByCurrentState();
    }
}
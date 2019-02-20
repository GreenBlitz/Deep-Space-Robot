package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.complex.hidden.elevator.ElevatorAboveCruise;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.ElevatorCrossingCruiseFromAbove;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.ElevatorCrossingCruiseFromBelow;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.wpi.first.wpilibj.command.Command;

public class MoveElevatorTo extends DynamicCommand {
    private static final Elevator.Level CRITICAL_LEVEL = Elevator.Level.CRUISE;

    private Elevator.Level m_destination;

    public MoveElevatorTo(Elevator.Level height) {
        m_destination = height;
    }

    @Override
    protected Command pick() {
        var initalHeight = Elevator.getInstance().getLevel();

        var isInitialHigher = initalHeight.greater(CRITICAL_LEVEL);
        var isDestHigher = m_destination.greater(CRITICAL_LEVEL);

        if (isInitialHigher && isDestHigher) {
            // No interactions at all
            return new ElevatorAboveCruise(m_destination);
        }

        if (isInitialHigher) {
            // The elevator is higher than level1 and goes below it
            return new ElevatorCrossingCruiseFromAbove(m_destination.getHeight());
        }

        if (isDestHigher) {
            // The elevator is lower than level1 but goes above it
            return new ElevatorCrossingCruiseFromBelow(m_destination.getHeight());
        }

        return new MoveElevator(m_destination.getHeight());
    }
}

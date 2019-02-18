package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.complex.hidden.elevator.ElevatorAbove1;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.ElevatorCrossing1FromAbove;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.ElevatorCrossing1FromBelow;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.wpi.first.wpilibj.command.Command;

public class MoveElevatorTo extends DynamicCommand {
    private static final Elevator.Level CRITICAL_LEVEL = Elevator.Level.CRUISE;

    private double m_destination;

    public MoveElevatorTo(double height) {
        m_destination = height;
    }

    @Override
    protected Command pick() {
        var initalHeight = Elevator.getInstance().getHeight();

        var isInitialHigher = initalHeight >= CRITICAL_LEVEL.getHeight();
        var isDestHigher = m_destination >= CRITICAL_LEVEL.getHeight();

        if (isInitialHigher && isDestHigher) {
            // No interactions at all
            return new ElevatorAbove1(m_destination);
        }

        if (isInitialHigher) {
            // The elevator is higher than level1 and goes below it
            return new ElevatorCrossing1FromAbove(m_destination);
        }

        if (isDestHigher) {
            // The elevator is lower than level1 but goes above it
            return new ElevatorCrossing1FromBelow(m_destination);
        }

        return new MoveElevator(m_destination);
    }
}

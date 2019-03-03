package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;

public class MoveElevator extends DynamicCommand {
    private double m_height, m_lowerTol, m_higherTol;

    public MoveElevator(double height, double lowerTol, double higherTol) {
        m_height = height;
        m_lowerTol = lowerTol;
        m_higherTol = higherTol;
        setName("Move elevator to " + height);
    }

    public MoveElevator(Elevator.Level level, double lower, double higher){
        this(level.heightByCurrentState(), lower, higher);
    }

    @Override
    protected GBCommand pick() {
        if (Elevator.getInstance().getHeight() > m_height) {
            // Going down
            return new MoveElevator(m_height, m_lowerTol, m_higherTol);
        } else {
            // Going up
            return new MoveElevator(m_height, m_higherTol, m_lowerTol);
        }
    }
}
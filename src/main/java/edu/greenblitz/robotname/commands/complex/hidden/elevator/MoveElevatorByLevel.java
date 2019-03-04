package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevatorByExternalPID;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;

public class MoveElevatorByLevel extends DynamicCommand {
    private double m_lowerTol, m_higherTol;
    private Elevator.Level m_level;

    public MoveElevatorByLevel(Elevator.Level height, double lowerTol, double higherTol) {
        m_level = height;
        m_lowerTol = lowerTol;
        m_higherTol = higherTol;
        setName("Move elevator to " + height);
    }

    public MoveElevatorByLevel(Elevator.Level level){
        this(level, Elevator.LOWER_TOLERANCE, Elevator.HIGHER_TOLERANCE);
    }

    @Override
    protected GBCommand pick() {
        var state = OI.getOIState();
        if (Elevator.getInstance().getHeight() > m_level.heightByState(state)) {
            // Going down
            return new MoveElevatorByExternalPID(m_level.heightByState(state), m_lowerTol, m_higherTol);
        } else {
            // Going up
            return new MoveElevatorByExternalPID(m_level.heightByState(state), m_higherTol, m_lowerTol);
        }
    }
}
package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.simple.elevator.VerboseMoveElevatorByExternalPID;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;

public class VerboseMoveElevator extends DynamicCommand {
    private double m_height, m_lowerTol, m_higherTol;

    public VerboseMoveElevator(double height, double lowerTol, double higherTol) {
        m_height = height;
        m_lowerTol = lowerTol;
        m_higherTol = higherTol;
        setName("Verbose move elevator to " + height);
    }

    public VerboseMoveElevator(double height) {
        this(height, Elevator.LOWER_TOLERANCE, Elevator.HIGHER_TOLERANCE);
    }

    public VerboseMoveElevator(Elevator.Level level) {
        this(level.heightByCurrentState());
    }

    public VerboseMoveElevator(Elevator.Level level, double lower, double higher){
        this(level.heightByCurrentState(), lower, higher);
    }

    @Override
    protected GBCommand pick() {
        if (Elevator.getInstance().getHeight() > m_height) {
            // Going down
            return new VerboseMoveElevatorByExternalPID(m_height, m_lowerTol, m_higherTol);
        } else {
            // Going up
            return new VerboseMoveElevatorByExternalPID(m_height, m_higherTol, m_lowerTol);
        }
    }

}

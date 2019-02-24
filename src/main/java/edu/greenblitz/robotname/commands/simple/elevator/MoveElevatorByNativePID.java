package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;

public class MoveElevatorByNativePID extends AbstractNativeElevatorMove {
    public MoveElevatorByNativePID(Elevator.Level destination, int loopIdx, long timeOnTarget) {
        super(destination, loopIdx, timeOnTarget);
    }

    @Override
    void startNativeMove() {
        system.setPosition(m_destination);
    }
}

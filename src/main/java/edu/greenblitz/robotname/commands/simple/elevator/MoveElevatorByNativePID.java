package edu.greenblitz.robotname.commands.simple.elevator;

public class MoveElevatorByNativePID extends AbstractNativeElevatorMove {
    public MoveElevatorByNativePID(double destination, int loopIdx, long timeOnTarget) {
        super(destination, loopIdx, timeOnTarget);
    }

    @Override
    void startNativeMove() {
        system.setPosition(destination);
    }
}

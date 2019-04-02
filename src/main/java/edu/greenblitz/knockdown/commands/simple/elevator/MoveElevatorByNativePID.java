package edu.greenblitz.knockdown.commands.simple.elevator;

public class MoveElevatorByNativePID extends AbstractNativeElevatorMove {
    public MoveElevatorByNativePID(double destination, int loopIdx, long timeOnTarget) {
        super(destination, loopIdx, timeOnTarget);
    }

    @Override
    protected void startNativeMove(double level) {
        system.setPosition(level);
    }
}

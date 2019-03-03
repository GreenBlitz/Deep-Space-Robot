package edu.greenblitz.robotname.commands.simple.elevator;

public class MoveElevatorByMagic extends AbstractNativeElevatorMove {
    public MoveElevatorByMagic(double destination, int loopIdx, long timeOnTarget) {
        super(destination, loopIdx, timeOnTarget);
    }

    @Override
    void startNativeMove(double level) {
        system.setSmartPosition(level);
    }
}
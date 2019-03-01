package edu.greenblitz.utils.sm;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.Elevator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum ElevatorState {
    GROUND(Elevator.Level.GROUND.heightByState(OI.State.CARGO)),
    UP(Elevator.Level.CARGO_SHIP.heightByState(OI.State.CARGO));

    public final double height;

    ElevatorState(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public int getLevel() {
        return ordinal();
    }

    public static List<ElevatorState> getList() {
        return Arrays.asList(values());
    }

   public static ElevatorState closestTo(Elevator.Level level) {
        return level == Elevator.Level.GROUND ? GROUND : UP;
    }

    public static ElevatorState closestTo(double destination) {
        return destination <= UP.height ? GROUND : UP;
    }
}

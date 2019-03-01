package edu.greenblitz.robotname.data.sm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum ElevatorState {
    GROUND(0),
    DOWN(0),
    UP(0);

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

    public static ElevatorState closestTo(double height) {
        return Arrays.stream(values()).min(Comparator.comparingDouble(o -> Math.abs(o.height - height))).orElse(GROUND);
    }
}

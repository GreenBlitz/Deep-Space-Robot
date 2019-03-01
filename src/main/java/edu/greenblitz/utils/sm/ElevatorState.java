package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//TODO: Change to only 2 states: one for when the roller is "in danger" (DOWN) and one for when it is not (UP).
//TODO: closestTo should match ElevatorLevel and not raw height.
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

    //TODO: change from height based and closet to to range based (move height bound, higher height bound)
    public static ElevatorState closestTo(double height) {
        return Arrays.stream(values()).min(Comparator.comparingDouble(o -> Math.abs(o.height - height))).orElse(GROUND);
    }
}

package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum ElevatorState {

    GROUND,
    DOWN,
    UP;

    public int getLevel(){
        return ordinal();
    }


    public static List<ElevatorState> getList(){
        return Arrays.asList(values());
    }


}

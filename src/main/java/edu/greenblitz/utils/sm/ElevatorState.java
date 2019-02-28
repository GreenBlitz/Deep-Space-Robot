package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum ElevatorState {

    LEVEL0,
    HATCH1,
    CARGO1,
    HATCH2,
    CARGO2,
    HATCH3,
    CARGO3;

    public int getLevel(){
        return ordinal();
    }


    public static List<ElevatorState> getList(){
        return Arrays.asList(values());
    }


}

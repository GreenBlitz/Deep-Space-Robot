package edu.greenblitz.robotname.data.sm;

import java.util.Arrays;
import java.util.List;

public enum RollerState {
    ROLLER_IN, ROLLER_OUT;

    public static List<RollerState> getList(){
        return Arrays.asList(values());
    }
}

package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum RollerState {
    RETRACTED, EXTENDED;

    public static List<RollerState> getList(){
        return Arrays.asList(values());
    }
}

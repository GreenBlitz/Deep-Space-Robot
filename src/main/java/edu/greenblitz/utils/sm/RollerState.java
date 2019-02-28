package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum RollerState {
    IN, OUT;

    public static List<RollerState> getList(){
        return Arrays.asList(values());
    }
}

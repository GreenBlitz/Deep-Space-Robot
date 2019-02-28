package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum KickerState {
    KICK, UNKICK;

    public static List<KickerState> getList(){
        return Arrays.asList(values());
    }
}

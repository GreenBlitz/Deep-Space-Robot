package edu.greenblitz.robotname.data.sm;

import java.util.Arrays;
import java.util.List;

public enum PokerState {
    POKING, UNPOKING;

    public static List<PokerState> getList(){
        return Arrays.asList(values());
    }
}

package edu.greenblitz.utils.sm;

import edu.greenblitz.robotname.subsystems.Poker;

import java.util.Arrays;
import java.util.List;

public enum PokerState {
    POKING, UNPOKING;

    public static List<PokerState> getList(){
        return Arrays.asList(values());
    }
}

package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum PokerState {
    POKING, UNPOKING /*, PokerFace */;

    public static final int LENGTH = 2; /* number of States, should be updated*/
    public static final List<PokerState> lst = Arrays.asList(PokerState.values()); /* for indexing peruses */


    @Override
    public String toString() {
        return this.name();
    }

}

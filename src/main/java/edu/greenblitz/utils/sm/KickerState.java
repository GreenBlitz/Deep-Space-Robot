package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum KickerState {
    KICK, UNKICK;

    public static final int LENGTH = 2; /* number of States, should be updated*/
    public static final List<KickerState> lst = Arrays.asList(KickerState.values()); /* for indexing peruses */

    @Override
    public String toString() {
        return this.name();
    }
}

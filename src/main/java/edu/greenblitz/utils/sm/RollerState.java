package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum RollerState {
    IN, OUT;

    public static final int LENGTH = 2; /* number of States, should be updated*/
    public static final List<RollerState> lst = Arrays.asList(RollerState.values()); /* for indexing peruses */


    @Override
    public String toString() {
        return this.name();
    }

}

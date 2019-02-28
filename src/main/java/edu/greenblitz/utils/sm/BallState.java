package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum BallState {
    HAVE, DONT_HAVE;

    public static final int LENGTH = 2; /* number of States ,should be updated*/
    public static final List<BallState> lst = Arrays.asList(BallState.values()); /* for indexing peruses */

    @Override
    public String toString() {
        return this.name();
    }
}

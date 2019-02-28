package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum BallState {
    HAVE, DONT_HAVE;

    public static List<BallState> getList(){
        return Arrays.asList(values());
    }

}

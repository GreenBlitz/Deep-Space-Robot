package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public enum ElevatorState {

    LEVEL0(0),
    HATCH1(1),
    CARGO1(2),
    HATCH2(3),
    CARGO2(4),
    HATCH3(5),
    CARGO3(6);

    /* TODO: sort this by rising height (the height the elevetor needs to be in for putting the hatch or cargo);
     * Beware: that order in source code is important! */
    public static final int LENGTH = 7; /* number of States, should be updated*/
    public static final List<ElevatorState> lst = Arrays.asList(ElevatorState.values()); /* for indexing peruses */


    private int _level;

    ElevatorState(int level) {
        this._level = level;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public int getLevel() {
        return _level;
    }


}

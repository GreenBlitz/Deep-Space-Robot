package edu.greenblitz.utils.sm;

import java.util.Arrays;

public class State {
    public static final int BASE = 16, /*Used for HASHCODE() and to make states readable by some mean. should satisfy BASE => S.LENGTH */
            COUNT = ElevatorState.values().length * RollerState.values().length * PokerState.values().length * BallState.values().length
                    * KickerState.values().length;
    private ElevatorState m_ElevatorState;
    private RollerState m_RollerState;
    private PokerState m_PokerState;
    private BallState m_BallState;
    private KickerState m_KickerState;

    public State(ElevatorState ElevatorState, RollerState RollerState, PokerState PokerState, BallState BallState, KickerState KickerState) {
        this.m_ElevatorState = ElevatorState;
        this.m_RollerState = RollerState;
        this.m_PokerState = PokerState;
        this.m_BallState = BallState;
        this.m_KickerState = KickerState;
    }

    /***
     * returns the representation in base BASE; @return = ERPBK in base BASE (each letter is a digit in base BASE).
     * */
    @Override
    public int hashCode() {
        int ret = 0;

        assert (0 <= ElevatorState.getList().indexOf(m_ElevatorState) && ElevatorState.getList().indexOf(m_ElevatorState) < BASE);
        ret += ElevatorState.getList().indexOf(m_ElevatorState);
        ret *= BASE;

        assert (0 <= RollerState.getList().indexOf(m_RollerState) && RollerState.getList().indexOf(m_RollerState) < BASE);
        ret += RollerState.getList().indexOf(m_RollerState);
        ret *= BASE;

        assert (0 <= PokerState.getList().indexOf(m_PokerState) && PokerState.getList().indexOf(m_PokerState) < BASE);
        ret += PokerState.getList().indexOf(m_PokerState);
        ret *= BASE;

        assert (0 <= BallState.getList().indexOf(m_BallState) && BallState.getList().indexOf(m_BallState) < BASE);
        ret += BallState.getList().indexOf(m_BallState);
        ret *= BASE;

        assert (0 <= KickerState.getList().indexOf(m_KickerState) && KickerState.getList().indexOf(m_KickerState) < BASE);
        ret += KickerState.getList().indexOf(m_KickerState);

        return ret;
    }

}

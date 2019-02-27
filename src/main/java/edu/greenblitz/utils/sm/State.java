package edu.greenblitz.utils.sm;

public class State {
    public static final int BASE = 16, /*Used for HASHCODE() and to make states readable by some mean. should satisfy BASE => S.LENGTH */
            COUNT = ElevatorState.LENGTH * RollerState.LENGTH * PokerState.LENGTH * BallState.LENGTH * KickerState.LENGTH;
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

        assert (0 < ElevatorState.lst.indexOf(m_ElevatorState) && ElevatorState.lst.indexOf(m_ElevatorState) < BASE);
        ret += ElevatorState.lst.indexOf(m_ElevatorState);
        ret *= BASE;

        assert (0 < RollerState.lst.indexOf(m_RollerState) && RollerState.lst.indexOf(m_RollerState) < BASE);
        ret += RollerState.lst.indexOf(m_RollerState);
        ret *= BASE;

        assert (0 < PokerState.lst.indexOf(m_PokerState) && PokerState.lst.indexOf(m_PokerState) < BASE);
        ret += PokerState.lst.indexOf(m_PokerState);
        ret *= BASE;

        assert (0 < BallState.lst.indexOf(m_BallState) && BallState.lst.indexOf(m_BallState) < BASE);
        ret += BallState.lst.indexOf(m_BallState);
        ret *= BASE;

        assert (0 < KickerState.lst.indexOf(m_KickerState) && KickerState.lst.indexOf(m_KickerState) < BASE);
        ret += KickerState.lst.indexOf(m_KickerState);

        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode() && obj instanceof State;
    }
}

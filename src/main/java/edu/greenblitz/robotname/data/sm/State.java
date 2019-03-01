package edu.greenblitz.robotname.data.sm;

public class State {
    public static final int BASE = 16, /*Used for HASHCODE() and to make states readable by some mean. should satisfy BASE => S.LENGTH */
            COUNT = ElevatorState.values().length * RollerState.values().length * PokerState.values().length * KickerState.values().length;
    private ElevatorState m_elevatorState;
    private RollerState m_rollerState;
    private PokerState m_pokerState;
    private KickerState m_kickerState;

    public State(ElevatorState ElevatorState, RollerState RollerState, PokerState PokerState, KickerState KickerState) {
        this.m_elevatorState = ElevatorState;
        this.m_rollerState = RollerState;
        this.m_pokerState = PokerState;
        this.m_kickerState = KickerState;
    }

    public ElevatorState getElevatorState() {
        return m_elevatorState;
    }

    public void setElevatorState(ElevatorState m_ElevatorState) {
        this.m_elevatorState = m_ElevatorState;
    }

    public RollerState getRollerState() {
        return m_rollerState;
    }

    public void setRollerState(RollerState m_RollerState) {
        this.m_rollerState = m_RollerState;
    }

    public PokerState getPokerState() {
        return m_pokerState;
    }

    public void setPokerState(PokerState m_PokerState) {
        this.m_pokerState = m_PokerState;
    }

    public KickerState getKickerState() {
        return m_kickerState;
    }

    public void setKickerState(KickerState m_KickerState) {
        this.m_kickerState = m_KickerState;
    }

    /***
     * returns the representation in base BASE; @return = ERPBK in base BASE (each letter is a digit in base BASE).
     * */
    @Override
    public int hashCode() {
        int ret = 0;

        assert (0 <= ElevatorState.getList().indexOf(m_elevatorState) && ElevatorState.getList().indexOf(m_elevatorState) < BASE);
        ret += ElevatorState.getList().indexOf(m_elevatorState);
        ret *= BASE;

        assert (0 <= RollerState.getList().indexOf(m_rollerState) && RollerState.getList().indexOf(m_rollerState) < BASE);
        ret += RollerState.getList().indexOf(m_rollerState);
        ret *= BASE;

        assert (0 <= PokerState.getList().indexOf(m_pokerState) && PokerState.getList().indexOf(m_pokerState) < BASE);
        ret += PokerState.getList().indexOf(m_pokerState);
        ret *= BASE;

        assert (0 <= KickerState.getList().indexOf(m_kickerState) && KickerState.getList().indexOf(m_kickerState) < BASE);
        ret += KickerState.getList().indexOf(m_kickerState);

        return ret;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof State))
            return false;
        State os = (State) other;
        return m_elevatorState == os.m_elevatorState &&
                m_rollerState == os.m_rollerState &&
                m_pokerState == os.m_pokerState &&
                m_kickerState == os.m_kickerState;
    }

    @Override
    public String toString() {
        return " {" +
                m_elevatorState +
                ", " + m_rollerState +
                ", " + m_pokerState +
                ", " + m_kickerState +
                "}";
    }

    public boolean hasChanges() {
        return m_elevatorState != null || m_kickerState != null || m_pokerState != null || m_rollerState != null;
    }

    public String differenceString(State second) {
        StringBuilder builder = new StringBuilder(" {");
        if (!m_elevatorState.equals(second.m_elevatorState))
            builder.append(second.m_elevatorState);
        else
            builder.append(" - ");
        if (!m_rollerState.equals(second.m_rollerState))
            builder.append(", ").append(second.m_rollerState);
        else
            builder.append(", - ");
        if (!m_pokerState.equals(second.m_pokerState))
            builder.append(", ").append(second.m_pokerState);
        else
            builder.append(", - ");
        if (!m_kickerState.equals(second.m_kickerState))
            builder.append(", ").append(second.m_kickerState);
        else
            builder.append(", - ");
        return builder.append("}").toString();
    }
}

package edu.greenblitz.robotname.data.sm;

public class StateMachineGenerator {

    public static StateMachine createMachine(State initialState) {
        StateMachine timeMachine = getEmptyStateMachine(initialState);
        allowElevatorTransitions(timeMachine);
        addRollerTransitions(timeMachine);
        addPokerTransitions(timeMachine);
        addKickerTransitions(timeMachine);
        return timeMachine;
    }

    private static StateMachine getEmptyStateMachine(State initialState) {
        StateMachine ret = new StateMachine(initialState);
        for (ElevatorState e : ElevatorState.getList())
            for (RollerState r : RollerState.getList())
                for (PokerState p : PokerState.getList())
                    for (KickerState k : KickerState.getList())
                        ret.add(new State(e, r, p, k));
        return ret;
    }

    private static void allowElevatorTransitions(StateMachine base) {
        for (PokerState p : PokerState.getList())
            for (KickerState k : KickerState.getList())
                for (ElevatorState es : ElevatorState.getList())
                    for (ElevatorState ee : ElevatorState.getList())
                        base.allow(new State(es, RollerState.EXTENDED, p, k),
                                new State(ee, RollerState.EXTENDED, p, k));
    }

    private static void addRollerTransitions(StateMachine base) {
        for (ElevatorState e : ElevatorState.getList())
            for (PokerState p : PokerState.getList())
                for (KickerState k : new KickerState[]{KickerState.UNKICK, KickerState.BALL}) {
                    base.allow(new State(e, RollerState.RETRACTED, p, k),
                            new State(e, RollerState.EXTENDED, p, k));
                    base.allow(new State(e, RollerState.EXTENDED, p, k),
                            new State(e, RollerState.RETRACTED, p, k));
                }

            for (PokerState p : PokerState.getList()) {
                base.allow(new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.KICK),
                        new State(ElevatorState.UP, RollerState.EXTENDED, p, KickerState.KICK));
                base.allow(new State(ElevatorState.UP, RollerState.EXTENDED, p, KickerState.KICK),
                        new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.KICK));
            }
    }

    private static void addPokerTransitions(StateMachine base) {
        for (ElevatorState e : ElevatorState.getList())
            for (KickerState k : new KickerState[]{KickerState.UNKICK, KickerState.BALL}) {
                base.allow(new State(e, RollerState.RETRACTED, PokerState.UNPOKING, k),
                        new State(e, RollerState.RETRACTED, PokerState.POKING, k));
                base.allow(new State(e, RollerState.RETRACTED, PokerState.POKING, k),
                        new State(e, RollerState.RETRACTED, PokerState.UNPOKING, k));
            }
        for (ElevatorState e : ElevatorState.getList())
            if (e != ElevatorState.UP)
                for (KickerState k : new KickerState[]{KickerState.UNKICK, KickerState.BALL}) {
                    base.allow(new State(e, RollerState.EXTENDED, PokerState.UNPOKING, k),
                            new State(e, RollerState.EXTENDED, PokerState.POKING, k));
                    base.allow(new State(e, RollerState.EXTENDED, PokerState.POKING, k),
                            new State(e, RollerState.EXTENDED, PokerState.UNPOKING, k));
                }
    }

    private static void addKickerTransitions(StateMachine base) {
            for (PokerState p : PokerState.getList()) {
                base.allow(new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.UNKICK),
                        new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.KICK));
                base.allow(new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.KICK),
                        new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.UNKICK));
                base.allow(new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.BALL),
                        new State(ElevatorState.UP, RollerState.RETRACTED, p, KickerState.KICK));
            }
        for (ElevatorState e : ElevatorState.getList())
            if (e != ElevatorState.UP)
                for (PokerState p : PokerState.getList()) {
                    base.allow(new State(e, RollerState.EXTENDED, p, KickerState.UNKICK),
                            new State(e, RollerState.EXTENDED, p, KickerState.KICK));
                    base.allow(new State(e, RollerState.EXTENDED, p, KickerState.KICK),
                            new State(e, RollerState.EXTENDED, p, KickerState.UNKICK));
                    base.allow(new State(e, RollerState.EXTENDED, p, KickerState.BALL),
                            new State(e, RollerState.EXTENDED, p, KickerState.KICK));
                }
        for (PokerState p : PokerState.getList())
        base.allow(new State(ElevatorState.GROUND, RollerState.EXTENDED, p, KickerState.UNKICK),
                new State(ElevatorState.GROUND, RollerState.EXTENDED, p, KickerState.BALL));
    }
}

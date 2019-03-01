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
        for (KickerState k : KickerState.getList())
            for (ElevatorState es : ElevatorState.getList())
                for (ElevatorState ee : ElevatorState.getList())
                    base.allow(new State(es, RollerState.EXTENDED, PokerState.UNPOKING, k),
                            new State(ee, RollerState.EXTENDED, PokerState.UNPOKING, k));
    }

    private static void addRollerTransitions(StateMachine base) {
        for (ElevatorState e : ElevatorState.getList()) {
            base.allow(new State(e, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.UNKICK),
                    new State(e, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.UNKICK));
            base.allow(new State(e, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.UNKICK),
                    new State(e, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.UNKICK));
        }

        for (KickerState k : KickerState.getList()) {
            base.allow(new State(ElevatorState.UP, RollerState.RETRACTED, PokerState.POKING, k),
                    new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.POKING, k));
            base.allow(new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.POKING, k),
                    new State(ElevatorState.UP, RollerState.RETRACTED, PokerState.POKING, k));
        }

        base.allow(new State(ElevatorState.UP, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.KICK),
                new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.KICK));
        base.allow(new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.KICK),
                new State(ElevatorState.UP, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.KICK));
    }

    private static void addPokerTransitions(StateMachine base) {
        for (ElevatorState e : ElevatorState.getList()) {
            base.allow(new State(e, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.UNKICK),
                    new State(e, RollerState.RETRACTED, PokerState.POKING, KickerState.UNKICK));
            base.allow(new State(e, RollerState.RETRACTED, PokerState.POKING, KickerState.UNKICK),
                    new State(e, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.UNKICK));
        }
        base.allow(new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.UNKICK),
                new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.POKING, KickerState.UNKICK));
        base.allow(new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.POKING, KickerState.UNKICK),
                new State(ElevatorState.UP, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.UNKICK));
    }

    private static void addKickerTransitions(StateMachine base) {
        for (RollerState r : RollerState.getList())
            for (PokerState p : PokerState.getList()) {
                base.allow(new State(ElevatorState.UP, r, p, KickerState.UNKICK),
                        new State(ElevatorState.UP, r, p, KickerState.KICK));
                base.allow(new State(ElevatorState.UP, r, p, KickerState.KICK),
                        new State(ElevatorState.UP, r, p, KickerState.UNKICK));
            }
        for (ElevatorState e : ElevatorState.getList())
            if (e != ElevatorState.UP) {
                base.allow(new State(e, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.UNKICK),
                        new State(e, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.KICK));
                base.allow(new State(e, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.KICK),
                        new State(e, RollerState.EXTENDED, PokerState.UNPOKING, KickerState.UNKICK));
            }
    }
}

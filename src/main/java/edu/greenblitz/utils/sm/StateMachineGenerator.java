package edu.greenblitz.utils.sm;

import java.util.Arrays;
import java.util.List;

public class StateMachineGenerator {

    public static StateMachine createMachine() {
        StateMachine machine = getEmptyStateMachine();
        allowElevatorTransitions(machine);
        addRollerTransitions(machine);
        addPokerTransitions(machine);
        addKickerTransitions(machine);
        return machine;
    }

    private static StateMachine getEmptyStateMachine() {
        StateMachine ret = new StateMachine();
        for (ElevatorState e : ElevatorState.getList())
            for (RollerState r : RollerState.getList())
                for (PokerState p : PokerState.getList())
                    for (KickerState k : KickerState.getList())
                        ret.add(new State(e, r, p, k));
        return ret;
    }

    private static void allowElevatorTransitions(StateMachine base) {
        List<ElevatorState> ues = Arrays.asList(getUninterruptedElevatorStates());
        for (PokerState p : PokerState.getList())
            for (KickerState k : KickerState.getList())
                for (ElevatorState es : ElevatorState.getList())
                    for (ElevatorState ee : ElevatorState.getList())
                        base.allow(new State(es, RollerState.ROLLER_OUT, p, k),
                                new State(ee, RollerState.ROLLER_OUT, p, k));
        for (PokerState p : PokerState.getList())
            for (KickerState k : KickerState.getList())
                for (ElevatorState es : ues)
                    for (ElevatorState ee : ues)
                        base.allow(new State(es, RollerState.ROLLER_IN, p, k),
                                new State(ee, RollerState.ROLLER_IN, p, k));
        for (ElevatorState es : ElevatorState.getList())
            if (!ues.contains(es))
                for (ElevatorState ee : ElevatorState.getList())
                    if (!ues.contains(ee))
                        for (PokerState p : PokerState.getList())
                            base.allow(new State(es, RollerState.ROLLER_IN, p, KickerState.UNKICK),
                                    new State(ee, RollerState.ROLLER_IN, p, KickerState.UNKICK));

    }

    private static ElevatorState[] getUninterruptedElevatorStates() {
        return new ElevatorState[]{ElevatorState.UP};
    }

    private static void addRollerTransitions(StateMachine base) {
        for (ElevatorState e : ElevatorState.getList())
            for (PokerState p : PokerState.getList())
                for (KickerState k : new KickerState[]{KickerState.UNKICK, KickerState.BALL}) {
                    base.allow(new State(e, RollerState.ROLLER_IN, p, k),
                            new State(e, RollerState.ROLLER_OUT, p, k));
                    base.allow(new State(e, RollerState.ROLLER_OUT, p, k),
                            new State(e, RollerState.ROLLER_IN, p, k));
                }

        for (ElevatorState e : getUninterruptedElevatorStates())
            for (PokerState p : PokerState.getList()) {
                base.allow(new State(e, RollerState.ROLLER_IN, p, KickerState.KICK),
                        new State(e, RollerState.ROLLER_OUT, p, KickerState.KICK));
                base.allow(new State(e, RollerState.ROLLER_OUT, p, KickerState.KICK),
                        new State(e, RollerState.ROLLER_IN, p, KickerState.KICK));
            }
    }

    private static void addPokerTransitions(StateMachine base) {
        List<ElevatorState> ues = Arrays.asList(getUninterruptedElevatorStates());
        for (ElevatorState e : ElevatorState.getList())
            for (KickerState k : new KickerState[]{KickerState.UNKICK, KickerState.BALL}) {
                base.allow(new State(e, RollerState.ROLLER_IN, PokerState.UNPOKING, k),
                        new State(e, RollerState.ROLLER_IN, PokerState.POKING, k));
                base.allow(new State(e, RollerState.ROLLER_IN, PokerState.POKING, k),
                        new State(e, RollerState.ROLLER_IN, PokerState.UNPOKING, k));
            }
        for (ElevatorState e : ElevatorState.getList())
            if (!ues.contains(e))
                for (KickerState k : new KickerState[]{KickerState.UNKICK, KickerState.BALL}) {
                    base.allow(new State(e, RollerState.ROLLER_OUT, PokerState.UNPOKING, k),
                            new State(e, RollerState.ROLLER_OUT, PokerState.POKING, k));
                    base.allow(new State(e, RollerState.ROLLER_OUT, PokerState.POKING, k),
                            new State(e, RollerState.ROLLER_OUT, PokerState.UNPOKING, k));
                }
    }

    private static void addKickerTransitions(StateMachine base) {
        List<ElevatorState> ues = Arrays.asList(getUninterruptedElevatorStates());
        for (ElevatorState e : ues)
            for (PokerState p : PokerState.getList()) {
                base.allow(new State(e, RollerState.ROLLER_IN, p, KickerState.UNKICK),
                        new State(e, RollerState.ROLLER_IN, p, KickerState.KICK));
                base.allow(new State(e, RollerState.ROLLER_IN, p, KickerState.KICK),
                        new State(e, RollerState.ROLLER_IN, p, KickerState.UNKICK));
                base.allow(new State(e, RollerState.ROLLER_IN, p, KickerState.BALL),
                        new State(e, RollerState.ROLLER_IN, p, KickerState.KICK));
            }
        for (ElevatorState e : ElevatorState.getList())
            if (!ues.contains(e))
                for (PokerState p : PokerState.getList()) {
                    base.allow(new State(e, RollerState.ROLLER_OUT, p, KickerState.UNKICK),
                            new State(e, RollerState.ROLLER_OUT, p, KickerState.KICK));
                    base.allow(new State(e, RollerState.ROLLER_OUT, p, KickerState.KICK),
                            new State(e, RollerState.ROLLER_OUT, p, KickerState.UNKICK));
                    base.allow(new State(e, RollerState.ROLLER_OUT, p, KickerState.BALL),
                            new State(e, RollerState.ROLLER_OUT, p, KickerState.KICK));
                    base.allow(new State(e, RollerState.ROLLER_OUT, p, KickerState.UNKICK),
                            new State(e, RollerState.ROLLER_OUT, p, KickerState.BALL));
                }
    }
}

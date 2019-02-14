package edu.greenblitz.robotname.data;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.utils.Tuple;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class InterwindSubsystems {
    public static class State {
        final RobotMap.Elevator.ElevatorLevel elevator;
        final DoubleSolenoid.Value roller;
        final boolean ball;

        private static State of(int elevator, int roller, int ball) {
            return new State(
                    RobotMap.Elevator.ElevatorLevel.values()[elevator],
                    roller == 0 ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward,
                    ball == 1);
        }

        private State(RobotMap.Elevator.ElevatorLevel elevator, DoubleSolenoid.Value roller, boolean ball) {
            this.elevator = elevator;
            this.roller = roller;
            this.ball = ball;
        }

        public Tuple<State, State> to(State other) {
            return new Tuple<>(this, other);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return elevator == state.elevator &&
                    roller == state.roller &&
                    ball == state.ball;
        }

        @Override
        public int hashCode() {
            return Objects.hash(elevator, roller, ball);
        }

        @Override
        public String toString() {
            return "State{" +
                    "elevator=" + elevator +
                    ", roller=" + roller +
                    ", ball=" + ball +
                    '}';
        }
    }

    public static final State EMPTY_AUTO = State.of(0, 0, 0);
    public static final State BALL_AUTO = State.of(1, 0, 1);
    public static final State BALL_COLLECTION = State.of(0, 1, 0);
    public static final State BALL_THROW = BALL_AUTO;

    public static final State STATE_000 = EMPTY_AUTO;
    public static final State STATE_011 = State.of(0, 1, 1);
    public static final State STATE_100 = State.of(1, 0, 0);
    public static final State STATE_010 = BALL_COLLECTION;
    public static final State STATE_110 = State.of(1, 1, 0);
    public static final State STATE_111 = State.of(1, 1, 1);
    public static final State STATE_101 = BALL_AUTO;

    private static final Set<Tuple<State, State>> UNWANTED_PASSES = Set.of(
            STATE_011.to(STATE_010),
            STATE_111.to(STATE_110)
    );

    private static final Set<Tuple<State, State>> GOOD_PASSES = Set.of(
            STATE_000.to(STATE_010), STATE_010.to(STATE_000),
            STATE_010.to(STATE_011),
            STATE_010.to(STATE_110), STATE_110.to(STATE_010),
            STATE_011.to(STATE_111), STATE_111.to(STATE_011),
            STATE_110.to(STATE_100), STATE_100.to(STATE_110),
            STATE_111.to(STATE_101), STATE_101.to(STATE_111),
            STATE_101.to(STATE_100)
    );

    private static final Set<Tuple<State, State>> WHITE_LIST;

    static {
        WHITE_LIST = new HashSet<>(UNWANTED_PASSES);
        WHITE_LIST.addAll(GOOD_PASSES);
    }

    public static boolean isLegal(Tuple<State, State> conn) {
        return WHITE_LIST.contains(conn);
    }

    public static boolean shouldBeAvoided(Tuple<State, State> conn) {
        return UNWANTED_PASSES.contains(conn);
    }

    public static boolean isLegal(State... states) {
        for (var i = 0; i < states.length; ++i) {
            if (!isLegal(states[i].to(states[i + 1])))
                return false;
        }

        return true;
    }
}
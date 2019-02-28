package edu.greenblitz.utils.sm;

import org.junit.jupiter.api.Test;

import static org.testng.Assert.*;

public class StateMachineTest {

    @Test
    public void isAllowed() {
        State start = new State(ElevatorState.CARGO3, RollerState.IN, PokerState.POKING, BallState.DONT_HAVE, KickerState.UNKICK);
        State end = new State(ElevatorState.CARGO3, RollerState.IN, PokerState.POKING, BallState.DONT_HAVE, KickerState.KICK);
        StateMachine machine = new StateMachine(start, end);
        assertFalse(machine.isAllowed(start, end));
        machine.allow(start, end);
        assertTrue(machine.isAllowed(start, end));
        assertFalse(machine.isAllowed(end, start));
    }
}
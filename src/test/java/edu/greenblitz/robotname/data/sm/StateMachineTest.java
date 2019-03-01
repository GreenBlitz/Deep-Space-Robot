package edu.greenblitz.robotname.data.sm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateMachineTest {

    @Test
    public void isAllowed() {
        State start = new State(ElevatorState.UP, RollerState.RETRACTED, PokerState.POKING, KickerState.UNKICK);
        State end = new State(ElevatorState.UP, RollerState.RETRACTED, PokerState.POKING, KickerState.KICK);
        StateMachine machine = new StateMachine(start);
        machine.add(end);
        assertFalse(machine.isAllowed(start, end));
        machine.allow(start, end);
        assertTrue(machine.isAllowed(start, end));
        assertFalse(machine.isAllowed(end, start));
    }



}
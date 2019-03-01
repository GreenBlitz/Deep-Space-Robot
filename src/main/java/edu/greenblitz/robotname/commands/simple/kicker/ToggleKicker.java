package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.utils.sm.KickerState;

public class ToggleKicker extends KickerBaseCommand {
    private static final long KICKER_TOGGLE_TIMEOUT = 1000;

    public ToggleKicker() {
        super(KICKER_TOGGLE_TIMEOUT);
    }

    @Override
    protected KickerState getNextState() {
        if (Robot.getInstance().getStateMachine().getCurrentState().getKickerState() == KickerState.KICK)
            return KickerState.UNKICK;
        else
            return KickerState.KICK;
    }

    @Override
    protected void initialize() {
        system.kick(!system.isOpen());
    }
}

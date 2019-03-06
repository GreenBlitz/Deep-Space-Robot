package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.utils.sm.KickerState;

import static edu.greenblitz.robotname.subsystems.Kicker.KICKER_STATE_TIMEOUT;

public class ToggleKicker extends KickerBaseCommand {
    public ToggleKicker() {
        super(KICKER_STATE_TIMEOUT);
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

package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.KickerState;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class ToggleKicker extends TimedSubsystemCommand<Kicker> {
    private static final long KICKER_TOGGEL_TIMEOUT = 1000;

    public ToggleKicker() {
        super(KICKER_TOGGEL_TIMEOUT, Kicker.getInstance());
    }


    @Override
    public State getDeltaState() {
        return new State(null, null, null,
                Robot.getInstance().getStatus().getCurrentState().getM_KickerState() == KickerState.KICK
                        ? KickerState.UNKICK : KickerState.KICK);
    }

    @Override
    protected void initialize() {
        system.kick(!system.isOpen());
    }
}

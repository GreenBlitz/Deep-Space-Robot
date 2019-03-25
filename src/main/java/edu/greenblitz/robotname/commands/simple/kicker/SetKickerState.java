package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.sm.KickerState;

public class SetKickerState extends KickerBaseCommand {
    private boolean m_shouldKick;

    public SetKickerState(boolean kick) {
        super(Kicker.KICKER_STATE_TIMEOUT);
        m_shouldKick = kick;
    }

    @Override
    protected void atInit() {
        system.kick(m_shouldKick);
    }

    @Override
    protected KickerState getNextState() {
        return null;
    }
}

package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.SetKickerState;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 * Sets the kicker state to given state if it isn't there already
 */
public class SmartSetKickerState extends ConditionalCommand {
    private boolean m_shouldKick;

    /**
     * Desired kicker state - true for open (kicking), false for closed (unkicking)
     * @param kick desired kicker state
     */
    public SmartSetKickerState(boolean kick) {
        super(new SetKickerState(kick));
        m_shouldKick = kick;
    }

    @Override
    protected boolean condition() {
        return Kicker.getInstance().isClosed() == m_shouldKick;
    }
}

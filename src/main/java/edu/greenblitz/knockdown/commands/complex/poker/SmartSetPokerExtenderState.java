package edu.greenblitz.knockdown.commands.complex.poker;

import edu.greenblitz.knockdown.commands.simple.poker.SetPokerExtenderState;
import edu.greenblitz.knockdown.subsystems.Poker;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 * Sets the poker state to given state if it isn't there already
 */
public class SmartSetPokerExtenderState extends ConditionalCommand {
    private boolean m_shouldExtend;

    /**
     * Desired poker state - true for open (extended), false for closed (retracted)
     * @param extend desired poker state
     */
    public SmartSetPokerExtenderState(boolean extend) {
        super(new SetPokerExtenderState(extend));
        m_shouldExtend = extend;
    }

    @Override
    protected boolean condition() {
        return Poker.getInstance().isRetracted() == m_shouldExtend;
    }
}

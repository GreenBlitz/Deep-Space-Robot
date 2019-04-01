package edu.greenblitz.robotname.commands.complex.roller;

import edu.greenblitz.robotname.commands.simple.roller.SetRollerExtenderState;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 * Sets the roller state to given state if it isn't there already
 */
public class SmartSetRollerExtenderState extends ConditionalCommand {
    private boolean m_shouldExtend;

    /**
     * Desired roller state - true for open (extended), false for closed (retracted)
     * @param extend desired roller state
     */
    public SmartSetRollerExtenderState(boolean extend) {
        super(new SetRollerExtenderState(extend));
        m_shouldExtend = extend;
    }

    @Override
    protected boolean condition() {
        return Roller.getInstance().isRetracted() == m_shouldExtend;
    }
}
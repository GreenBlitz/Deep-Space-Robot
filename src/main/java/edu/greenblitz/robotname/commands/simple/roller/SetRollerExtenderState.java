package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class SetRollerExtenderState extends RollerBaseCommand {
    private boolean m_shouldExtend;

    public SetRollerExtenderState(boolean extend) {
        m_shouldExtend = extend;
    }

    @Override
    protected void atInit() {
        system.setExtender(m_shouldExtend);
    }

    @Override
    protected RollerState getNextState() {
        return null;
    }
}

package edu.greenblitz.knockdown.commands.simple.roller;

public class SetRollerExtenderState extends RollerBaseCommand {
    private boolean m_shouldExtend;

    public SetRollerExtenderState(boolean extend) {
        m_shouldExtend = extend;
    }

    @Override
    protected void atInit() {
        system.setExtender(m_shouldExtend);
    }

}

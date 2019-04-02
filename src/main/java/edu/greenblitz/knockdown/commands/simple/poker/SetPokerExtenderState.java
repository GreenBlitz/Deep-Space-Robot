package edu.greenblitz.knockdown.commands.simple.poker;

public class SetPokerExtenderState extends PokerBaseCommand {
    private boolean m_shouldExtend;

    public SetPokerExtenderState(boolean extend) {
        m_shouldExtend = extend;
    }

    @Override
    protected void atInit() {
        system.extend(m_shouldExtend);
    }

}

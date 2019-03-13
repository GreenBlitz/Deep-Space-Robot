package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class SetPokerExtenderState extends PokerBaseCommand {
    private boolean m_shouldExtend;

    public SetPokerExtenderState(boolean extend) {
        m_shouldExtend = extend;
    }

    @Override
    protected void atInit() {
        system.extend(m_shouldExtend);
    }

    @Override
    protected PokerState getNextState() {
        return null;
    }
}

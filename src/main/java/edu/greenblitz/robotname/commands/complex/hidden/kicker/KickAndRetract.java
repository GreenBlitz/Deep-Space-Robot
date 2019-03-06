package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class KickAndRetract extends CommandChain {
    private long m_timeout;

    public KickAndRetract() {
        this(Kicker.KICKER_STATE_TIMEOUT);
    }

    public KickAndRetract(long timeout) {
        m_timeout = timeout;
    }

    public KickAndRetract(long timeout, String name) {
        super(name);
        m_timeout = timeout;
    }

    @Override
    protected void initChain() {
        addSequential(new Kick(m_timeout));
        addSequential(new Unkick(m_timeout));
    }
}

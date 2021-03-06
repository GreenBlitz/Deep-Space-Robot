package edu.greenblitz.knockdown.commands.simple.poker;

import edu.greenblitz.knockdown.subsystems.Poker;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class ReleaseHatch extends SubsystemCommand<Poker> {

    public ReleaseHatch() {
        super(400, Poker.getInstance());
    }

    public ReleaseHatch(long ms) {
        super(ms, Poker.getInstance());
    }

    @Override
    protected void atInit() {
        system.hold(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class ReleaseHatch extends SubsystemCommand<Poker> {

    public ReleaseHatch() {
        super(Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.hold(false);
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, null, null);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
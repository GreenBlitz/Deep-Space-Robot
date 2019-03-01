package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ReleaseHatch extends SubsystemCommand<Poker> {

    public ReleaseHatch() {
        super(Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.hold(false);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
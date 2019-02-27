package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ReleaseHatch extends SubsystemCommand<Poker> {

    public ReleaseHatch() {
        super(Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.hold(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
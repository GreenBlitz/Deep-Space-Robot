package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class PullFrontPoker extends SubsystemCommand<Poker> {

    public PullFrontPoker() {
        super(Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
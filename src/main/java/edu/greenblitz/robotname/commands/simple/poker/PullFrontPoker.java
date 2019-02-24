package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.FrontPoker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class PullFrontPoker extends SubsystemCommand<FrontPoker> {

    public PullFrontPoker() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.retract();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
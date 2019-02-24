package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.FrontPoker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class PushFrontPoker extends SubsystemCommand<FrontPoker> {

    public PushFrontPoker() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
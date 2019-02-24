package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.FrontPoker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ReleaseHatch extends SubsystemCommand<FrontPoker> {

    public ReleaseHatch() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.release();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
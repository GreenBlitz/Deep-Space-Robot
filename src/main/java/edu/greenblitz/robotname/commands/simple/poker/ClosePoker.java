package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.FrontPoker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ClosePoker extends SubsystemCommand<FrontPoker> {
    public ClosePoker() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.fullClose();
    }

    @Override
    protected boolean isFinished() {
        return system.isFullyClosed();
    }
}

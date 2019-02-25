package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.FrontPoker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class RetractPoker extends SubsystemCommand<FrontPoker> {
    public RetractPoker() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(false);
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }
}

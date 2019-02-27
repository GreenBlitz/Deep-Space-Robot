package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.FrontPoker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ExtendPoker extends SubsystemCommand<FrontPoker> {
    public ExtendPoker() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(true);
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }
}

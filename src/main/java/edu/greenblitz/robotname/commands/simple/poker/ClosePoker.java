package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ClosePoker extends SubsystemCommand<Poker> {
    public ClosePoker() {
        super(Poker.getInstance());
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

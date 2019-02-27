package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class PushFrontPoker extends SubsystemCommand<Poker> {

    public PushFrontPoker() {
        super(Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(true);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
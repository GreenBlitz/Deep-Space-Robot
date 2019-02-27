package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class RetractPoker extends SubsystemCommand<Poker> {
    public RetractPoker() {
        super(Poker.getInstance());
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

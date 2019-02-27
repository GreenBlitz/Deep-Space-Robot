package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class TogglePokerExtender extends SubsystemCommand<Poker> {
    public TogglePokerExtender() {
        super(Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(!system.isExtended());
//        system.extend(system.isExtendsed());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

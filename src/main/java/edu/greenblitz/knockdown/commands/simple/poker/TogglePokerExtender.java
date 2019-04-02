package edu.greenblitz.knockdown.commands.simple.poker;

public class TogglePokerExtender extends PokerBaseCommand {

    public TogglePokerExtender() {

    }

    @Override
    protected void atInit() {
        system.extend(!system.isExtended());
    }

}

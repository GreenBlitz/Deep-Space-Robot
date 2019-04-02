package edu.greenblitz.knockdown.commands.simple.poker;

public class RetractAndHold extends PokerBaseCommand {
    public RetractAndHold(long ms) {
        super(ms);
    }

    public RetractAndHold() {
    }

    @Override
    protected void atInit() {
        system.extend(false);
        system.hold(true);
    }

}
package edu.greenblitz.knockdown.commands.simple.poker;

public class ExtendAndHold extends PokerBaseCommand {
    public ExtendAndHold(long ms) {
        super(ms);
    }

    public ExtendAndHold() {}

    @Override
    protected void atInit() {
        system.extend(true);
        system.hold(true);
    }

}
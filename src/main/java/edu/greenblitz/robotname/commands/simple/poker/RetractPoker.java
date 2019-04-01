package edu.greenblitz.robotname.commands.simple.poker;

public class RetractPoker extends PokerBaseCommand {
    public RetractPoker() {
    }

    public RetractPoker(long ms) {
        super(ms);
    }

    @Override
    protected void atInit() {
        system.extend(false);
    }

}

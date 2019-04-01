package edu.greenblitz.robotname.commands.simple.poker;

public class ExtendPoker extends PokerBaseCommand {
    public ExtendPoker() {
    }

    public ExtendPoker(long ms) {
        super(ms);
    }

    @Override
    protected void atInit() {
        system.extend(true);
    }

}

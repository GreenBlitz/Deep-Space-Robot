package edu.greenblitz.knockdown.commands.simple.shifter;

import edu.greenblitz.knockdown.subsystems.Shifter;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public abstract class ShifterBaseCommand extends SubsystemCommand<Shifter> {
    public ShifterBaseCommand() {
        super(Shifter.getInstance());
    }

    public ShifterBaseCommand(String name) {
        super(name, Shifter.getInstance());
    }

    public ShifterBaseCommand(long ms) {
        super(ms, Shifter.getInstance());
    }

    public ShifterBaseCommand(String name, long ms) {
        super(name, ms, Shifter.getInstance());
    }

}
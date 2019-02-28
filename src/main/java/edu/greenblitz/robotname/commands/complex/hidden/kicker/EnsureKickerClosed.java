package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureKickerClosed extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new Unkick());
    }
}

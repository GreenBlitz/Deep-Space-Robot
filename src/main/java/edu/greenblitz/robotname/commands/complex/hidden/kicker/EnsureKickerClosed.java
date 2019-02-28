package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureKickerClosed extends CommandChain {
    public EnsureKickerClosed() {
        super((new Unkick()));
    }
}

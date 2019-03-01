package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureKickerClosed extends CommandChain {
    @Override
    protected void initChain() {
        if(Kicker.getInstance().isClosed()){
            addSequential(new NullCommand());
            return;
        }
        addSequential(new Unkick());
    }
}

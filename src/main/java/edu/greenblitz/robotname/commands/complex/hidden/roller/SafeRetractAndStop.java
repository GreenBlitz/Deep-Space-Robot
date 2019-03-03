package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.DelayCommand;
import edu.greenblitz.robotname.commands.simple.roller.StopRolling;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.chain.CommandChain;

public class SafeRetractAndStop extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new DynamicRequire(Roller.getInstance()));
        addSequential(new EnsureRollerRetracted());
        addSequential(new StopRolling());
    }
}

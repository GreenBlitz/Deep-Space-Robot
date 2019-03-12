package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.DelayCommand;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndRollIn;
import edu.greenblitz.robotname.commands.simple.roller.RollIn;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.Scheduler;

public class SafeExtendAndRollIn extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new EnsureRollerExtended());
        addSequential(new RollIn());
        addSequential(new DelayCommand(1000));
    }

    protected void end(){
        new RetractAndRollIn().start();
    }
}

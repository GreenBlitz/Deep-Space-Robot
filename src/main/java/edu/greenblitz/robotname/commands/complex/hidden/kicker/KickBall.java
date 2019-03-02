package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeExtendAndRollOut;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.chain.CommandChain;

public class KickBall extends CommandChain {
    @Override
    protected void initChain() {
        if (Elevator.getInstance().isFloorLevel())
            addParallel(new SafeExtendAndRollOut());
        else
            addParallel(new EnsureRollerRetracted());
        addSequential(new KickAndRetract());
        addSequential(new EnsureRollerRetracted());
    }
}

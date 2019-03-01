package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.chain.CommandChain;

public class SafeExtendAndRollOut extends CommandChain {
    @Override
    protected void initChain() {
        if (Elevator.getInstance().isFloorLevel()) {
            addSequential(new EnsurePokerRetracted());
        }
        addSequential(new ExtendAndRollIn());
    }
}

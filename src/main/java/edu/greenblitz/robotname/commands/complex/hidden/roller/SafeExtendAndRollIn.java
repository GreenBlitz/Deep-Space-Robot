package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.roller.RollIn;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.chain.CommandChain;

public class SafeExtendAndRollIn extends CommandChain {
    @Override
    protected void initChain() {
        if (Elevator.getInstance().isFloorLevel()) {
            addSequential(new EnsureKickerClosed());
            addSequential(new EnsurePokerRetracted());
        }
        if (Roller.getInstance().isExtended())
            addSequential(new ExtendAndRollIn());
        addSequential(new RollIn());
    }
}

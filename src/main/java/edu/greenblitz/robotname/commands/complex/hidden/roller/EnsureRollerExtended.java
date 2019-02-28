package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureRollerExtended extends CommandChain {
    public EnsureRollerExtended() {
        super(new EnsurePokerRetracted());
        addSequential(new ExtendRoller());
    }
}

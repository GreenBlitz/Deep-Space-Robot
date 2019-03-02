package edu.greenblitz.robotname.commands.complex.exposed.cargo;

import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.utils.command.chain.CommandChain;

public class CollectCargo extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new ExtendAndRollIn());
        addSequential(new EnsureRollerRetracted());
    }
}

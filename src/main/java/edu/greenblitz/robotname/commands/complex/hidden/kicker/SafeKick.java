package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;

public class SafeKick extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new WaitAndRequire(Poker.getInstance()));
        addParallel(new DynamicCommand() {
            @Override
            protected GBCommand pick() {
                if (!Elevator.getInstance().isFloorLevel())
                    return new NullCommand();
                return new ExtendRoller();
            }
        });
//        addSequential(new Kick());
//        addSequential(new Unkick());
    }
}

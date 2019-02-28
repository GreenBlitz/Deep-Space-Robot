package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.WaitUntilClear;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DoNothing;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.wpi.first.wpilibj.command.Command;

public class SafeKick extends CommandChain {
    public SafeKick() {
        super(new WaitAndRequire(Poker.getInstance()));
        addParallel(new DynamicCommand() {
            @Override
            protected GBCommand pick() {
                if (Elevator.getInstance().isFloorLevel())
                    return new DoNothing();
                return new ExtendRoller();
            }
        });
        addSequential(new kick());
        addSequential(new unkick());

    }
}

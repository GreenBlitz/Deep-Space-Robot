package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;

/**
 *
 */
public class EnsureRollerExtended extends CommandChain {
    @Override
    protected void initChain() {
        addParallel(new WaitAndRequire(Roller.getInstance()), new WaitAndRequire(Elevator.getInstance()));

        if (Roller.getInstance().isExtended()) {
            addSequential(new NullCommand());
            return;
        }

        addSequential(new DynamicCommand() {
            @Override
            protected GBCommand pick() {
                if (Elevator.getInstance().isFloorLevel() && Poker.getInstance().isExtended())
                    return new EnsurePokerRetracted();
                return new DynamicRequire(Poker.getInstance());
            }
        });

        addSequential(new ExtendRoller());
    }
}

package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.*;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;

public class KickBall extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new DynamicCommand("KickBall dynamic") {
            @Override
            protected GBCommand pick() {
                if (Elevator.getInstance().isFloorLevel()) return new KickAtFloor();
                else return new KickAtHeight();
            }
        });

        addSequential(new Unkick());
        addSequential(new RetractAndStop());
    }

    public static class KickAtFloor extends CommandChain {
        @Override
        protected void initChain() {
            addSequential(new RetractPoker());
            addSequential(new ExtendAndRollOut());
            addSequential(new Kick());
        }
    }

    public static class KickAtHeight extends CommandChain {
        @Override
        protected void initChain() {
            addSequential(new Kick());
        }
    }
}

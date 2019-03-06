package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.*;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
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
    }

    public static class KickAtFloor extends CommandChain {
        @Override
        protected void initChain() {
            addSequential(new DynamicCommand("unpoke if needed") {
                @Override
                protected GBCommand pick() {
                    if (Poker.getInstance().isRetracted()) return new DynamicRequire(Poker.getInstance());
                    else return new RetractPoker();
                }
            });
            addSequential(new ExtendAndRollOut(500));
            addSequential(new KickAndRetract(300));
            addSequential(new RetractAndStopRoller(800));
        }
    }

    public static class KickAtHeight extends CommandChain {
        @Override
        protected void initChain() {
            addSequential(new KickAndRetract());
        }
    }
}

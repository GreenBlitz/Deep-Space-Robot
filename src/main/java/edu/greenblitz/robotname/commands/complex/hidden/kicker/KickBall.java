package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.*;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.wpi.first.wpilibj.command.Command;

public class KickBall extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new DynamicCommand("KickBall dynamic") {
            @Override
            protected Command pick() {
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
            addSequential(new DynamicCommand("extend roller if needed") {
                @Override
                protected GBCommand pick() {
                    if (Roller.getInstance().isRetracted()) return new ExtendAndRollOut(300);
                    else return new RollOut(1);
                }
            });

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

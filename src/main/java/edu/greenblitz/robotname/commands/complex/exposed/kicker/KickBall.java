package edu.greenblitz.robotname.commands.complex.exposed.kicker;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickAndRetract;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollOut;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.robotname.commands.simple.roller.RollOut;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class KickBall extends CommandChain {
    public KickBall() {
        addSequential(new ConditionalCommand("KickBall dynamic", new KickAtFloor(), new KickAtHeight()) {
            @Override
            protected boolean condition() {
                return Elevator.getInstance().isFloorLevel();
            }
        });
    }

    public static class KickAtFloor extends CommandChain {
        public KickAtFloor() {
            addSequential(new ConditionalCommand("unpoke if needed", new RetractPoker(300)) {
                @Override
                protected boolean condition() {
                    return Poker.getInstance().isExtended();
                }
            });

            addSequential(new ConditionalCommand("extend roller if needed", new ExtendAndRollOut(300), new RollOut(1)) {
                @Override
                protected boolean condition() {
                    return Roller.getInstance().isRetracted();
                }
            });

            addSequential(new KickAndRetract(500));
            addSequential(new RetractAndStopRoller(800));
        }
    }

    public static class KickAtHeight extends CommandChain {
        public KickAtHeight() {
            addSequential(new KickAndRetract());
        }
    }

    @Override
    protected void atEnd() {
        Roller.getInstance().stopRolling();
        Roller.getInstance().retract();
    }
}

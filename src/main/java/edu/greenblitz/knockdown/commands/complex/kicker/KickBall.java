package edu.greenblitz.knockdown.commands.complex.kicker;

import edu.greenblitz.knockdown.commands.simple.kicker.Kick;
import edu.greenblitz.knockdown.commands.simple.poker.RetractPoker;
import edu.greenblitz.knockdown.commands.simple.roller.ExtendAndRollOut;
import edu.greenblitz.knockdown.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.knockdown.commands.simple.roller.RollOut;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.knockdown.subsystems.Poker;
import edu.greenblitz.knockdown.subsystems.Roller;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.command.WaitUntilFree;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class KickBall extends CommandChain {
    public KickBall() {
        addSequential(new WaitUntilFree(Elevator.getInstance()));
        addSequential(
                new ConditionalCommand("KickBall dynamic", new KickAtFloor(), new KickAtHeight()) {
                    @Override
                    protected boolean condition() {
                        return Elevator.getInstance().isFloorLevel();
                    }
                }
        );
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
            addSequential(new Kick());
        }
    }
}

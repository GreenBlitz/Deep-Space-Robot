package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollOut;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.commands.simple.roller.RollOut;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.BinaryChoiceCommand;

public class KickBall extends CommandChain {
    @Override
    protected void initChain() {
        addParallel(new SafeRollerExtension(), new RetractPoker());
        addSequential(new Kick());
        addSequential(new Unkick());
        addSequential(new RetractRoller());
    }

    private static class SafeRollerExtension extends BinaryChoiceCommand {
        public SafeRollerExtension() {
            super(new RollOut(), new ExtendAndRollOut());
        }

        @Override
        protected Boolean state() {
            var isGround = true;//Elevator.getInstance().isFloorLevel();
            var isExtended = Roller.getInstance().isExtended();
            return isGround && !isExtended;
        }
    }
}

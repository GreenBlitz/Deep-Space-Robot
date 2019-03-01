package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollOut;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.BinaryChoiceCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;

public class SafeKick extends CommandChain {
    @Override
    protected void initChain() {
        addParallel(new SafeRollerExtension(), new WaitAndRequire(Poker.getInstance()));
        addSequential(new Kick());
        addSequential(new Unkick());
    }

    private static class SafeRollerExtension extends BinaryChoiceCommand {
        public SafeRollerExtension() {
            super(new NullCommand(), new ExtendAndRollOut());
        }

        @Override
        protected Boolean state() {
            var isGround = true;//Elevator.getInstance().isFloorLevel();
            var isExtended = Roller.getInstance().isExtended();
            return isGround && !isExtended;
        }
    }
}

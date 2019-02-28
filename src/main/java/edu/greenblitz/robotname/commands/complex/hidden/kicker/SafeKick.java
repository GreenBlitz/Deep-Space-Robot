package edu.greenblitz.robotname.commands.complex.hidden.kicker;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.BinaryChoiceCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;

public class SafeKick extends CommandChain {
    @Override
    protected void initChain() {
        addParallel(new SafeRollerExtension(), new WaitAndRequire(Poker.getInstance()));
        addSequential(new Kick());
        addSequential(new Unkick());
    }

    private static class SafeRollerExtension extends BinaryChoiceCommand {
        public SafeRollerExtension() {
            super(new NullCommand(), new ExtendRoller());
        }

        @Override
        protected Boolean state() {
            return Elevator.getInstance().isFloorLevel();
        }
    }
}

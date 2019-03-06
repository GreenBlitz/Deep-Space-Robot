package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class SafeMoveElevator extends CommandChain {
    private double height;

    public SafeMoveElevator(double height) {
        this.height = height;
    }

    public SafeMoveElevator(Elevator.Level level) {
        this(level.heightByCurrentState());
    }

    @Override
    protected void initChain() {
        addSequential(new DynamicCommand("Ensuring roller safe") {
            @Override
            protected GBCommand pick() {
                if (Elevator.isBelowCritical(height) || Elevator.isBelowCritical(Elevator.getInstance().getHeight()))
                    return new GroundMovement();
                return new NullCommand();
            }
        });
        addSequential(new MoveElevator(height));
        addSequential(new RetractRoller(300));
    }

    private static class GroundMovement extends CommandChain {

        @Override
        protected void initChain() {
            addSequential(new RetractPoker(200));
            addSequential(new ExtendRoller(300));
        }

    }

}
package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.simple.elevator.BrakeElevator;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.sm.ElevatorState;

public class SafeMoveElevator extends CommandChain {

    double height;

    public SafeMoveElevator(double height) {
        System.out.println("dab");
        this.height = height;
    }

    public SafeMoveElevator(Elevator.Level level) {
        this(level.heightByCurrentState());
    }

    @Override
    protected void initChain() {
        setInterruptible(false);

        System.out.println("Moving to " + height + " from " + Elevator.getInstance().getHeight());
        addSequential(new DynamicRequire(Elevator.getInstance(), Roller.getInstance(), Poker.getInstance(), Kicker.getInstance()));

        if ((ElevatorState.getStateByHeight(Elevator.getInstance().getLevel()) == ElevatorState.GROUND ||
                ElevatorState.getStateByHeight(height) == ElevatorState.GROUND) && Roller.getInstance().isRetracted()) {
            addSequential(new BrakeElevator());

            if (Poker.getInstance().isExtended()) {
                if (Kicker.getInstance().isOpen())
                    addSequential(new Unkick());
                addSequential(new RetractPoker());
            }
            addSequential(new ExtendRoller());

            if (Elevator.getInstance().getHeight() > height) { // Going down
                addSequential(new MoveElevator(height, .02, 1));
            } else { // Going up
                addSequential(new MoveElevator(height, 1, .02));
            }
            addSequential(new RetractRoller());
        } else {
            if (Elevator.getInstance().getHeight() > height) { // Going down
                addSequential(new MoveElevator(height, .01, 1));
            } else { // Going up
                addSequential(new MoveElevator(height, 1, .01));
            }
        }
    }

}
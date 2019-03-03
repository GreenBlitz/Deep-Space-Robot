package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.sm.ElevatorState;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SafeMoveElevator extends CommandChain {

    double height;

    public SafeMoveElevator(double height) {
        this.height = height;
    }

    @Override
    protected void initChain() {
        setInterruptible(false);

        System.out.println("Moving to " + height + " from " + Elevator.getInstance().getHeight());

        if ((ElevatorState.closestTo(Elevator.getInstance().getLevel()) == ElevatorState.GROUND ||
                ElevatorState.closestTo(height) == ElevatorState.GROUND) && Roller.getInstance().isRetracted()) {

            if (Poker.getInstance().isExtended()) {
                if (Kicker.getInstance().isOpen())
                    addSequential(new Unkick());
                addSequential(new RetractPoker());
            }
            addSequential(new ExtendRoller());

            if (Elevator.getInstance().getHeight() > height) { // Going down
                addSequential(new MoveElevator(height, .1, .02));
            } else { // Going up
                addSequential(new MoveElevator(height, .02, .1));
            }
            addSequential(new RetractRoller());
        } else {
            if (Elevator.getInstance().getHeight() > height) { // Going down
                addSequential(new MoveElevator(height, .1, .02));
            } else { // Going up
                addSequential(new MoveElevator(height, .02, .1));
            }
        }
    }

    public SafeMoveElevator(Elevator.Level level) {
        this(level.heightByCurrentState());
    }
}
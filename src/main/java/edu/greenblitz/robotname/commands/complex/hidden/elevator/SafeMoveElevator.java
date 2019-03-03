package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.simple.elevator.ImmediateBrakeElevator;
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
import edu.wpi.first.wpilibj.GenericHID;

public class SafeMoveElevator extends CommandChain {

    private static final double LOWER_TOLERANCE = 0.01;
    private static final double HIGHER_TOLERANCE = 1;

    private double height;

    public SafeMoveElevator(double height) {
        this.height = height;
    }

    public SafeMoveElevator(Elevator.Level level) {
        this(level.heightByCurrentState());
    }

    @Override
    protected void initChain() {
        addSequential(new DynamicRequire(Elevator.getInstance(), Roller.getInstance(), Poker.getInstance(), Kicker.getInstance()));

        if ((ElevatorState.getStateByHeight(Elevator.getInstance().getLevel()) == ElevatorState.GROUND ||
                ElevatorState.getStateByHeight(height) == ElevatorState.GROUND) && Roller.getInstance().isRetracted()) {
            addSequential(new ImmediateBrakeElevator());

            if (Poker.getInstance().isExtended()) {
                if (Kicker.getInstance().isOpen()) {
                    addSequential(new Unkick());
                }
                addSequential(new RetractPoker());
            }
            addSequential(new ExtendRoller());

            addSequential(new MoveElevator(height, LOWER_TOLERANCE, HIGHER_TOLERANCE));

            addSequential(new RetractRoller());
        } else {
            addSequential(new MoveElevator(height, LOWER_TOLERANCE, HIGHER_TOLERANCE));
        }
    }

}
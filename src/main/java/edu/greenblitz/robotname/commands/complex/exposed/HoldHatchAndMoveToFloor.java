package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.complex.exposed.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class HoldHatchAndMoveToFloor extends CommandChain {
    public HoldHatchAndMoveToFloor() {
        addSequential(new RetractAndHold());
        addSequential(new ConditionalCommand("Move elevator to Ground if needed", new SafeMoveElevator(Elevator.Level.GROUND)) {
            @Override
            protected boolean condition() {
                return !Elevator.getInstance().isFloorLevel();
            }
        });
    }
}

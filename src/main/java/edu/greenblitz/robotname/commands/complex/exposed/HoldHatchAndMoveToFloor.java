package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.complex.exposed.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class HoldHatchAndMoveToFloor extends CommandChain {
    public HoldHatchAndMoveToFloor() {
        addSequential(new HoldHatch());
        addSequential(new ConditionalCommand("Move elevator to Ground if needed", new NullCommand(), new SafeMoveElevator(Elevator.Level.GROUND)) {
            @Override
            protected boolean condition() {
                return Elevator.getInstance().isFloorLevel();
            }
        });
    }
}

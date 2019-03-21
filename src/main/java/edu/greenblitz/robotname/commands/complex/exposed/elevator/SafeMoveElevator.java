package edu.greenblitz.robotname.commands.complex.exposed.elevator;

import edu.greenblitz.robotname.commands.complex.hidden.elevator.MoveElevatorByLevel;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.commands.simple.roller.StopRolling;
import edu.greenblitz.robotname.subsystems.*;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SafeMoveElevator extends CommandChain {

    public SafeMoveElevator(Elevator.Level level) {
        addSequential(new ConditionalCommand("Ensuring roller safe", new GroundMovement()) {
            @Override
            protected boolean condition() {
                return Elevator.isBelowCritical(level.heightByCurrentState()) || Elevator.isBelowCritical(Elevator.getInstance().getHeight());
            }
        });

        addSequential(new MoveElevatorByLevel(level));
        addSequential(new RetractRoller(300));

        clearRequirements();
        requires(Elevator.getInstance());
        requires(Roller.getInstance());
    }

    private static class GroundMovement extends CommandChain {
        public GroundMovement() {
            addParallel(new Unkick(150), new StopRolling());
            addSequential(new RetractPoker(200));
            addSequential(new ExtendRoller(300));
        }
    }
}
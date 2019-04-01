package edu.greenblitz.robotname.commands.complex.elevator;

import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.commands.simple.roller.StopRolling;
import edu.greenblitz.robotname.subsystems.*;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.Command;

public class SafeMoveElevator extends Command {

    private Elevator.Level level;

    public SafeMoveElevator(Elevator.Level level) {
        this.level = level;
    }

    @Override
    protected void initialize() {
        if (Elevator.isBelowCritical(level.heightByCurrentState())
                || Elevator.isBelowCritical(Elevator.getInstance().getHeight()))
            new GroundMovement(this.level).start();
        else
            new PreformMovement(this.level).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    private static class PreformMovement extends CommandChain {
        public PreformMovement(Elevator.Level level){
            addSequential(new MoveElevatorByLevel(level));
            addSequential(new RetractRoller(300));
        }
    }

    private static class GroundMovement extends CommandChain {

        private Elevator.Level level;

        public GroundMovement(Elevator.Level lvl) {
            setInterruptible(false);
            level = lvl;
            addParallel(new Unkick(150), new StopRolling());
            addSequential(new RetractPoker(200));
            addSequential(new ExtendRoller(300));
        }

        @Override
        protected void atInterrupt(){ } // IMPORTANT TO DO NOTHING

        @Override
        protected void atEnd(){
            new PreformMovement(level).start();
        }
    }
}
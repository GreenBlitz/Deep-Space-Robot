package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;

public class SafeMoveElevator extends CommandChain {

    private Elevator.Level m_level;
    private double m_height;

    public SafeMoveElevator(Elevator.Level level) {
        m_level = level;
    }

    @Override
    protected void initChain() {
        m_height = m_level.heightByCurrentState();

        addSequential(new DynamicCommand("Ensuring roller safe") {
            @Override
            protected GBCommand pick() {
                if (Elevator.isBelowCritical(m_height) || Elevator.isBelowCritical(Elevator.getInstance().getHeight()))
                    return new GroundMovement();
                return new NullCommand();
            }
        });
        addSequential(new MoveElevator(m_height));
        addParallel(new DynamicRequire(Elevator.getInstance()), new RetractRoller(300));
    }

    private static class GroundMovement extends CommandChain {

        @Override
        protected void initChain() {
            addSequential(new RetractPoker(200));
            addSequential(new ExtendRoller(300));
        }

    }
}
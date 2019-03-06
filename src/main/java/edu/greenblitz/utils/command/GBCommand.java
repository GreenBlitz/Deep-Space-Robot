package edu.greenblitz.utils.command;

import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class GBCommand extends Command {
    protected static final Logger logger = LogManager.getLogger(GBCommand.class);

    private static final Field requirements;
    private static final Field requirementsSet;

    static {
        try {
            Class WPISet = Class.forName("edu.wpi.first.wpilibj.command.Set");
            requirements = WPISet.getDeclaredField("m_set");
            requirements.setAccessible(true);
            requirementsSet = Command.class.getDeclaredField("m_requirements");
            requirementsSet.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequirements(Iterable<Subsystem> systems) {
        for (Subsystem s : systems)
            requires(s);
    }

    public final Set<Subsystem> getRequirements() {
        var ret = new HashSet<>(getWPILibRequirements());
        ret.addAll(getLazyRequirements());
        return ret;
    }

    /**
     * Reflection is love, reflection is life
     *
     * @return the wpilib managed requirements of this command
     */
    @SuppressWarnings("unchecked")
    public final Set<Subsystem> getWPILibRequirements() {
        try {
            var wpiRequires = (Vector<Subsystem>) requirements.get(requirementsSet.get(this));
            return new HashSet<>(wpiRequires);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Subsystem> getLazyRequirements() {
        return Set.of();
    }

    public GBCommand() {
    }

    public GBCommand(String name) {
        super(name);
    }

    public GBCommand(double timeout) {
        super(timeout);
    }

    public GBCommand(Subsystem subsystem) {
        super(subsystem);
    }

    public GBCommand(String name, Subsystem subsystem) {
        super(name, subsystem);
    }

    public GBCommand(double timeout, Subsystem subsystem) {
        super(timeout, subsystem);
    }

    public GBCommand(String name, double timeout) {
        super(name, timeout);
    }

    public GBCommand(String name, double timeout, Subsystem subsystem) {
        super(name, timeout, subsystem);
    }

    protected void reportCommandStart() {
        logger.debug("command {} has started!", getName());
    }

    protected void reportCommandEnd() {
        logger.debug("command {} has ended!", getName());
    }

    protected void reportCommandInterrupt() {
        logger.debug("command {} was interrupted", getName());
    }

    public abstract Optional<State> getDeltaState();

    @Override
    public final synchronized void start() {
//        State currState = Robot.getInstance().getStateMachine().getCurrentState();
//        var newStateOpt = getDeltaState();
//        State newState;
//        if (newStateOpt.isPresent()) {
//            newState = newStateOpt.get();
//            if (newState.getElevatorState() == null)
//                newState.setElevatorState(currState.getElevatorState());
//            if (newState.getKickerState() == null)
//                newState.setKickerState(currState.getKickerState());
//            if (newState.getPokerState() == null)
//                newState.setPokerState(currState.getPokerState());
//            if (newState.getRollerState() == null)
//                newState.setRollerState(currState.getRollerState());
//        } else {
//            newState = currState;
//        }
//
//        if (!Robot.getInstance().getStateMachine().isAllowed(currState, newState)) {
//            logger.warn("command {} aborted due to invalid state change: origin - {}, delta - {}",
//                    getName(), Robot.getInstance().getCurrentState(), getDeltaState());
////        } else {
//        }
//            Robot.getInstance().getStateMachine().setCurrentState(newState);
        reportCommandStart();
        super.start();
        atStart();
    }

    public boolean canRun() {
//        State currState = Robot.getInstance().getStateMachine().getCurrentState();
//        var newStateOpt = getDeltaState();
//        if (newStateOpt.isEmpty()) return true;
//
//        var newState = newStateOpt.get();
//        if (newState.getElevatorState() == null)
//            newState.setElevatorState(currState.getElevatorState());
//        if (newState.getKickerState() == null)
//            newState.setKickerState(currState.getKickerState());
//        if (newState.getPokerState() == null)
//            newState.setPokerState(currState.getPokerState());
//        if (newState.getRollerState() == null)
//            newState.setRollerState(currState.getRollerState());
//
//        return Robot.getInstance().getStateMachine().isAllowed(currState, newState);
        return true;
    }

    @Override
    protected final void end() {
        atEnd();
        super.end();
        reportCommandEnd();
    }

    @Override
    protected void interrupted() {
        atInterrupt();
        reportCommandInterrupt();
    }

    protected void atInterrupt() {
        atEnd();
    }

    protected void atEnd() {
    }

    protected void atStart() {
    }
}

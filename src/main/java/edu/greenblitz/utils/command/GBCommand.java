package edu.greenblitz.utils.command;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

public abstract class GBCommand extends Command {
    protected static final Logger logger = LogManager.getLogger(GBCommand.class);

    public GBCommand() {
    }

    public void addRequirements(Iterable<Subsystem> systems){
        for (Subsystem s : systems)
            requires(s);
    }

    @SuppressWarnings("unchecked")
    public final Set<Subsystem> getRequirements() {
        var ret = new HashSet<>(getWPILibRequirements());
        ret.addAll(getLazyRequirements());
        return ret;
    }

    @SuppressWarnings("unchecked")
    public final Set<Subsystem> getWPILibRequirements() {
        try {
            Class WPISet = Class.forName("edu.wpi.first.wpilibj.command.Set");
            var requirements = WPISet.getDeclaredField("m_set");
            requirements.setAccessible(true);
            var requirementSet = Command.class.getDeclaredField("m_requirements");
            requirementSet.setAccessible(true);
            var wpiRequires = (Vector<Subsystem>) requirements.get(requirementSet.get(this));
            return new HashSet<>(wpiRequires);
        } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Subsystem> getLazyRequirements() {
        return Set.of();
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

    abstract public Optional<State> getDeltaState();

    @Override
    public synchronized void start() {
        if (!canRun()) {
            logger.warn("command {} aborted due to invalid state change: origin - {}, delta - {}",
                    getName(), Robot.getInstance().getCurrentState(), getDeltaState());
        } else {
            reportCommandStart();
            super.start();
        }
    }

    public boolean canRun(){
        State currState = Robot.getInstance().getStateMachine().getCurrentState();
        var newStateOpt = getDeltaState();
        if (newStateOpt.isEmpty()) return true;

        var newState = newStateOpt.get();
        if (newState.getElevatorState() == null)
            newState.setElevatorState(currState.getElevatorState());
        if (newState.getKickerState() == null)
            newState.setKickerState(currState.getKickerState());
        if (newState.getPokerState() == null)
            newState.setPokerState(currState.getPokerState());
        if (newState.getRollerState() == null)
            newState.setRollerState(currState.getRollerState());

        return Robot.getInstance().getStateMachine().isAllowed(currState, newState);
    }

    @Override
    protected void end() {
        super.end();
        reportCommandEnd();
    }
}

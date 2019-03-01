package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public abstract class CommandChain extends GBCommand {

    protected Queue<ParallelCommand> m_commands = new LinkedList<>();
    protected Set<Subsystem> requiresSoFar = new HashSet<>();
    private ParallelCommand m_currentCommand;

    public CommandChain() {
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, null, null);
    }

    public CommandChain(String name) {
        super(name);
    }

    public CommandChain(double timeout) {
        super(timeout);
    }

    public CommandChain(String name, double timeout) {
        super(name, timeout);
    }

    public void addSequential(GBCommand command) {
        m_commands.add(new ParallelCommand(command.getName(), command));
    }

    public void addParallel(GBCommand... commands) {
        m_commands.add(new ParallelCommand(commands));
    }

    protected abstract void initChain();

    @Override
    protected final void initialize() {
        resetChain();
        initChain();
        updateCurrentCommand();
    }

    @Override
    protected void execute() {
        if (!m_currentCommand.isFinished())
            return;

        if (!m_commands.isEmpty()) {
            updateCurrentCommand();
        }
    }

    @Override
    protected boolean isFinished() {
        return m_commands.isEmpty();
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return requiresSoFar;
    }

    private void resetChain() {
        m_commands.clear();
        requiresSoFar.clear();
    }

    private void updateCurrentCommand() {
        m_currentCommand = m_commands.remove();
        logger.debug("update current command: {}", m_currentCommand);
        m_currentCommand.addRequirements(requiresSoFar);
        requiresSoFar.addAll(m_currentCommand.getRequirements());
        m_currentCommand.start();
    }
}
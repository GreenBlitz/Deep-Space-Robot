package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.*;

public abstract class CommandChain extends GBCommand {

    private Queue<ParallelCommand> m_commands = new LinkedList<>();
    private ParallelCommand m_currentCommand;

    public CommandChain() {
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
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
        m_commands.add(new ParallelCommand(command));
    }

    public void addParallel(GBCommand... commands) {
        m_commands.add(new ParallelCommand(commands));
    }

    protected abstract void initChain();

    @Override
    protected final void initialize() {
        resetChain();
        initChain();
        addSequential(new NullCommand()); // To ensure that the commands ends AFTER each child did
        updateCurrentCommand();
    }

    @Override
    protected void execute() {
        if (m_currentCommand.isCanceled()) {
            cancel();
            return;
        }

        if (!m_currentCommand.isFinished())
            return;

        if (!m_commands.isEmpty()) {
            updateCurrentCommand();
        }
    }

    @Override
    protected boolean isFinished() {
        return m_commands.isEmpty() && (m_currentCommand.isCanceled() || m_currentCommand.isCompleted()) && !m_currentCommand.isRunning();
    }

    @Override
    public Set<GBSubsystem> getLazyRequirements() {
        Set<GBSubsystem> ret = new HashSet<>();
        for (ParallelCommand c : m_commands)
            ret.addAll(c.getRequirements());
        return ret;
    }

    private void resetChain() {
        m_commands.clear();
    }

    private void updateCurrentCommand() {
        m_currentCommand = m_commands.remove();
        logger.debug("update current command: {}", m_currentCommand);
        m_currentCommand.start();
    }

    private void finishChain() {
        m_commands.clear();
        m_currentCommand.cancel();
        m_currentCommand = new ParallelCommand(new NullCommand());
    }

    @Override
    public synchronized void cancel() {
        finishChain();
        super.cancel();
    }

    @Override
    protected void atInterrupt() {
        logger.debug("{}: command {} was interrupted, aborting chain", getName(), m_currentCommand);
    }
}
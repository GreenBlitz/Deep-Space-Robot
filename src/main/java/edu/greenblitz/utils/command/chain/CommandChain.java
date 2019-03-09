package edu.greenblitz.utils.command.chain;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.greenblitz.utils.sm.State;

import java.util.*;

public abstract class CommandChain extends GBCommand {

    protected Queue<ParallelCommand> m_commands = new LinkedList<>();
    protected Set<GBSubsystem> m_requiresSoFar = new HashSet<>();
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
        addSequential(new NullCommand()); // To ensure that the commands ends AFTER each child did
        if (!updateCurrentCommand()){
            var cmd = m_commands.peek();
//            logger.warn(
//                    "chain {} aborted due to invalid state change - command: {}, states: from {}, delta {}",
//                    getName(), cmd.getName(), Robot.getInstance().getCurrentState(), cmd.getDeltaState());
            m_commands.clear();
            m_currentCommand = new ParallelCommand(new NullCommand());
        }
    }

    @Override
    protected void execute() {
        if (!m_currentCommand.isFinished())
            return;

        if (!m_commands.isEmpty()) {
            if (!updateCurrentCommand()) {
//                var cmd = m_commands.peek();
//                logger.warn(
//                        "chain {} aborted due to invalid state change - command: {}, states: from {}, delta {}",
//                        getName(), cmd.getName(), Robot.getInstance().getCurrentState(), cmd.getDeltaState());
                m_commands.clear();
            }
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
            ret.addAll(c.getLazyRequirements());
        return ret;
    }

    private void resetChain() {
        m_commands.clear();
        m_requiresSoFar.clear();
    }

    private boolean updateCurrentCommand() {
        if (!m_commands.peek().canRun()) {
            return false;
        }

        m_currentCommand = m_commands.remove();
        logger.debug("update current command: {}", m_currentCommand);
        m_currentCommand.addRequirements(m_requiresSoFar);
        m_requiresSoFar.addAll(m_currentCommand.getRequirements());
        m_currentCommand.start();
        return true;
    }
}
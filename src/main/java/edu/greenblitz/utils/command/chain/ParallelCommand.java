package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.robotname.data.sm.State;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ParallelCommand extends GBCommand {
    private GBCommand[] m_commands;

    @Override
    public Optional<State> getDeltaState() {
        State ret = new State(null, null, null, null);
        var nullState = new State(null, null, null, null);

        for (GBCommand c : m_commands)
            updateState(ret, c.getDeltaState().orElse(nullState));

        return Optional.ofNullable(ret.equals(nullState) ? null : ret);
    }

    private State updateState(State current, State toAdd) {
        if (toAdd.getElevatorState() != null) {
            if (current.getElevatorState() != null && current.getElevatorState() != toAdd.getElevatorState()) {
                throw new RuntimeException("Two commands with conflicting states.");
            }
            current.setElevatorState(toAdd.getElevatorState());
        }
        if (toAdd.getKickerState() != null) {
            if (current.getKickerState() != null && current.getKickerState() != toAdd.getKickerState()) {
                throw new RuntimeException("Two commands with conflicting states.");
            }
            current.setKickerState(toAdd.getKickerState());
        }
        if (toAdd.getPokerState() != null) {
            if (current.getPokerState() != null && current.getPokerState() != toAdd.getPokerState()) {
                throw new RuntimeException("Two commands with conflicting states.");
            }
            current.setPokerState(toAdd.getPokerState());
        }
        if (toAdd.getRollerState() != null) {
            if (current.getRollerState() != null && current.getRollerState() != toAdd.getRollerState()) {
                throw new RuntimeException("Two commands with conflicting states.");
            }
            current.setRollerState(toAdd.getRollerState());
        }
        return current;
    }

    public ParallelCommand(GBCommand... commands) {
        this(0, commands);
    }

    public ParallelCommand(String name, GBCommand... commands) {
        this(name, 0, commands);
    }

    public ParallelCommand(double timeout, GBCommand... commands) {
        this(Arrays.toString(commands), timeout, commands);
    }

    public ParallelCommand(String name, double timeout, GBCommand... commands) {
        super(name, timeout);
        m_commands = commands;
        addParallel();
        getDeltaState();
    }

    private void addParallel() {
        Arrays.stream(m_commands).flatMap(cmd -> cmd.getRequirements().stream()).forEach(this::requires);
    }

    public GBCommand[] getCommands() {
        return m_commands;
    }

    public boolean contains(GBCommand command) {
        return Arrays.asList(m_commands).contains(command);
    }

    private void runCommands() {
        Arrays.stream(m_commands).forEach(GBCommand::start);
    }

    @Override
    public synchronized void start() {
        runCommands();
    }

    @Override
    protected boolean isFinished() {
        for (var cmd : m_commands) {
            if (cmd.isCanceled()) return true;
        }

        for (var cmd : m_commands) {
            if (!cmd.isCompleted()) return false;
        }

        return true;
    }

    @Override
    protected void end() {
        Arrays.stream(m_commands).forEach(Command::cancel);
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return Arrays.stream(m_commands).flatMap(lst -> lst.getRequirements().stream()).collect(Collectors.toSet());
    }
}
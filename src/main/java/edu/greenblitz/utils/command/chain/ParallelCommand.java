package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class ParallelCommand extends GBCommand {
    private GBCommand[] m_commands;

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
    protected void end(){
        Arrays.stream(m_commands).forEach(Command::cancel);
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return Arrays.stream(m_commands).flatMap(lst -> lst.getRequirements().stream()).collect(Collectors.toSet());
    }
}
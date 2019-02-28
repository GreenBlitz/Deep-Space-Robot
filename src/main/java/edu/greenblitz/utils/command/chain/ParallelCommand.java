package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.*;
import java.util.stream.Collectors;

public class ParallelCommand extends GBCommand {
    private List<GBCommand> m_commands = new LinkedList<>();

    public ParallelCommand(GBCommand command) {
        addParallel(command);
    }

    public void addParallel(GBCommand command) {
        m_commands.add(command);
    }

    public List<GBCommand> getParallelCommands() {
        return m_commands;
    }

    public boolean contains(GBCommand command) {
        return m_commands.contains(command);
    }

    public void runCommands() {
        m_commands.forEach(x -> Scheduler.getInstance().add(x));
    }

    public boolean doesRequire(Subsystem subsystem) {
        return m_commands.stream().anyMatch(x -> x.doesRequire(subsystem));
    }

    @Override
    protected boolean isFinished() {
        return m_commands.stream().noneMatch(Command::isRunning) ||
                m_commands.stream().anyMatch(Command::isCanceled);
    }

    @Override
    protected void end(){
        m_commands.forEach(Command::cancel);
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return m_commands.stream().flatMap(lst -> lst.getRequirements().stream()).collect(Collectors.toSet());
    }
}
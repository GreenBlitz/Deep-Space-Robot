package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.*;

public class CommandChain extends GBCommand {

    protected Queue<ParallelCommand> commands;
    protected Set<Subsystem> requiresSoFar;

    public CommandChain(GBCommand initial){
        commands = new LinkedList<>();
        requiresSoFar = new HashSet<>();
        commands.add(new ParallelCommand(initial));
    }

    public void addSequential(GBCommand command){
        commands.add(new ParallelCommand(command));
    }

    public void addParallel(GBCommand command) {
        commands.peek().addParallel(command);
    }

    @Override
    protected void initialize(){
        Scheduler.getInstance().add(commands.peek());
    }

    @Override
    protected void execute() {
        ParallelCommand current = commands.peek();

        if (!current.isFinished())
            return;

        requiresSoFar.addAll(commands.remove().getRequirements());
        if (!commands.isEmpty()) {
            commands.peek().addRequirements(requiresSoFar);
            Scheduler.getInstance().add(commands.peek());
        }
    }

    @Override
    protected boolean isFinished() {
        return commands.isEmpty();
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return requiresSoFar;
    }
}
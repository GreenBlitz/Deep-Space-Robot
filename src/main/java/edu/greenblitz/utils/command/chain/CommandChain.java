package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.*;

public class CommandChain extends GBCommand {

    protected Queue<ParallelCommand> commands;
    protected List<Subsystem> requiresSoFar;

    public CommandChain(GBCommand initial){
        commands = new LinkedList<>();
        requiresSoFar = new ArrayList<>();
        commands.add(new ParallelCommand(initial));
    }

    public void addSequential(GBCommand command){
        commands.add(new ParallelCommand(command));
    }

    public void addParallel(GBCommand command) {
        commands.peek().addParallel(command);
    }

    private ParallelCommand current;

    @Override
    protected void initialize(){
        Scheduler.getInstance().add(commands.peek());
    }

    @Override
    protected void execute() {
        current = commands.peek();

        if (!current.isFinished())
            return;

        if (commands.size() > 0)
            commands.remove().runCommands();

    }

    @Override
    protected boolean isFinished() {
        return commands.isEmpty();
    }

    @Override
    public List<Subsystem> getRequirements() {
        return requiresSoFar;
    }
}
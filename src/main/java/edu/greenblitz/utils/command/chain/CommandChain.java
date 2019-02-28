package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.apache.logging.log4j.Logger;
import org.greenblitz.debug.logger.LoggerManager;

import java.util.*;

public abstract class CommandChain extends GBCommand {

    protected Queue<ParallelCommand> commands = new LinkedList<>();
    protected Set<Subsystem> requiresSoFar = new HashSet<>();

    public CommandChain() {
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

    public void addSequential(GBCommand command){
        logger.debug("Added command " + command + " as sequential");
        commands.add(new ParallelCommand(command));
    }

    public void addParallel(GBCommand command) {
        logger.debug("Added command " + command + " as parallel");
        commands.peek().addParallel(command);
    }

    protected abstract void initChain();

    @Override
    protected final void initialize(){
        resetChain();
        initChain();
        requiresSoFar.addAll(commands.peek().getRequirements());
        Scheduler.getInstance().add(commands.peek());
    }

    @Override
    protected void execute() {
        ParallelCommand current = commands.peek();

        if (!current.isFinished())
            return;

        logger.debug("Command batch " + current + " has finished.");

        commands.remove();
        if (!commands.isEmpty()) {
            requiresSoFar.addAll(commands.peek().getRequirements());
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

    private void resetChain() {
        commands.clear();
        requiresSoFar.clear();
        addSequential(new NullCommand());
    }
}
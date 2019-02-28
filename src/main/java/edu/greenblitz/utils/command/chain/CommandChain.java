package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.*;

public class CommandChain extends GBCommand {

    protected Queue<List<ChainableCommand>> commands;
    protected List<Subsystem> requiresSoFar;

    public CommandChain(ChainableCommand initial){
        commands = new LinkedList<>();
        requiresSoFar = new ArrayList<>();
        commands.add(Collections.singletonList(initial));
    }

    public void addSequential(ChainableCommand command){
        commands.add(Collections.singletonList(command));
    }

    public void addParallel(ChainableCommand command) {
        commands.peek().add(command);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    private List<ChainableCommand> current;
    private boolean someoneRunning;
    @Override
    protected void execute() {
        current = commands.peek();
        someoneRunning = false;

        for (ChainableCommand cc : current){
            if (cc.isCanceled()){
                cleanUp();
                return;
            }
            if (cc.isRunning())
                someoneRunning = true;
        }

        if (!someoneRunning){
            for (ChainableCommand c : current)
                requiresSoFar.addAll(c.getRequirements());
            for (ChainableCommand c : commands.remove()) {
                c.addRequirements(requiresSoFar);
                Scheduler.getInstance().add(c);
            }
        }

    }


    protected void cleanUp(){
        List<ChainableCommand> current = commands.peek();
        for (ChainableCommand cmd : current)
            cmd.cancel();
        commands.clear();
    }

    @Override
    protected boolean isFinished() {
        return commands.isEmpty();
    }
}
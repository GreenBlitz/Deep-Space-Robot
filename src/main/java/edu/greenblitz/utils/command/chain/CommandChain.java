package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.*;


public abstract class CommandChain extends CommandGroup {

    public CommandChain() {
        initChain();
    }

    public CommandChain(long timeout){
        setTimeout(timeout / 1000.0);
    }

    public CommandChain(String name) {
        super(name);
        initChain();
    }

    public void addParallel(Command first, Command... cmd){
        addSequential(first);
        for (Command c : cmd)
            addParallel(c);
    }

    protected abstract void initChain();


}
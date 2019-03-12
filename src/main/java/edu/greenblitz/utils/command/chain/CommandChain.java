package edu.greenblitz.utils.command.chain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class CommandChain extends CommandGroup {

    protected final Logger logger = LogManager.getLogger(CommandChain.class);

    public CommandChain() {
    }

    public CommandChain(long timeout) {
        setTimeout(timeout / 1000.0);
    }

    public CommandChain(String name) {
        super(name);
    }

    public void addParallel(Command first, Command... cmd) {
        addSequential(first);
        for (Command c : cmd)
            addParallel(c);
    }

    @Override
    protected final void initialize() {
        logger.debug("initializing command {}...", getName());
        atInit();
        logger.debug("command {} has been initialized!", getName());
    }

    @Override
    protected final void end() {
        logger.debug("ending command {}...", getName());
        atEnd();
        logger.debug("command {} has been ended!", getName());
    }

    @Override
    protected void interrupted() {
        logger.debug("interrupting command {}", getName());
        atInterrupt();
        logger.debug("command {} has been interrupted!");
    }

    protected void atInit() {
    }

    protected void atEnd() {
    }

    protected void atInterrupt() {
        atEnd();
    }
}
package edu.greenblitz.utils.command.dynamic;

import edu.wpi.first.wpilibj.command.Command;

import java.util.logging.Logger;

/**
 * A command whose behaviour is set when it is started, by choosing and running another command.
 * and logs the name of the chosen command. Basically, it's a way more generalized version of {@link edu.wpi.first.wpilibj.command.ConditionalCommand}
 *
 * <p>
 * A {@code DynamicCommand} requires no subsystems, and will stay in the scheduler as long as the chosen command does.
 * IT calls the chosen commands {@link Command#start()} when it's own is called,
 * </p>
 * <p>
 * Use {@link DynamicCommand#logger} to log additional data about the chosen command
 * </p>
 *
 * @see edu.wpi.first.wpilibj.command.ConditionalCommand
 */
public abstract class DynamicCommand extends Command {
    protected static Logger logger = Logger.getLogger("chosen commands");

    private Command chosen;

    @Override
    public synchronized void start() {
        chosen = pick();
        logger.fine(chosen.getName());
        chosen.start();
    }

    @Override
    protected void end() {
        chosen = null;
    }

    @Override
    protected boolean isFinished() {
        return chosen.isCompleted();
    }

    protected abstract Command pick();
}

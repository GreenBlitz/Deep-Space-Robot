package edu.greenblitz.utils.command.dynamic;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * A command whose behaviour is set when it is started, by choosing and running another command.
 * and logs the name of the chosen command. Basically, it's a way more generalized version of {@link edu.wpi.first.wpilibj.command.ConditionalCommand}
 *
 * <p>
 * A {@code DynamicCommand} requires no subsystems, and will stay in the scheduler as long as the chosen command does.
 * It starts the chosen commands {@link Command#start()} when it's own is called,
 * </p>
 * <p>
 * Use {@link DynamicCommand#logger} to log additional data about the chosen command
 * </p>
 *
 * @see edu.wpi.first.wpilibj.command.ConditionalCommand
 *
 * @deprecated The idea was good but doesn't integrate with {@link edu.wpi.first.wpilibj.command.CommandGroup CommandGroup} at all, use {@link edu.wpi.first.wpilibj.command.ConditionalCommand ConditionalCommand}
 */
@Deprecated
public abstract class DynamicCommand extends GBCommand {
    protected static Logger logger = LogManager.getLogger("chosen commands");

    private Command chosen;

    public DynamicCommand() {
    }

    public DynamicCommand(String name) {
        super(name);
    }

    @Override
    protected void atInit() {
        chosen = pick();
        logger.debug(chosen);
        chosen.start();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
        if (chosen.isCanceled()) {
            cancel();
        }
    }

    @Override
    protected boolean isFinished() {
        return chosen.isCompleted();
    }

    @Override
    public synchronized void cancel() {
        logger.debug("aborting dynamic command {} due to child interruption", getName());
        super.cancel();
    }

    @Override
    protected void atEnd() {
        if (chosen.isRunning()) chosen.cancel();
    }

    protected abstract Command pick();
}

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
 * IT calls the chosen m_commands {@link Command#start()} when it's own is called,
 * </p>
 * <p>
 * Use {@link DynamicCommand#logger} to log additional data about the chosen command
 * </p>
 *
 * @see edu.wpi.first.wpilibj.command.ConditionalCommand
 */
public abstract class DynamicCommand extends GBCommand {
    protected static Logger logger = LogManager.getLogger("chosen commands");

    private GBCommand chosen;
    private State delta = new State();

    @Override
    public synchronized void start() {
        super.start();
        chosen = pick();
        delta = chosen.getDeltaState().orElse(new State());
        logger.debug(chosen);
        chosen.start();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.ofNullable(delta.hasChanges() ? delta : null);
    }

    @Override
    protected void end() {
        super.end();
        chosen = null;
    }

    @Override
    protected boolean isFinished() {
        return chosen.isCompleted();
    }

    protected abstract GBCommand pick();
}

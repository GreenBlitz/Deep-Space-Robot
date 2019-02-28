package edu.greenblitz.utils.command.dynamic;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Command;

import java.util.Map;

/**
 * The most likely implementation of {@link DynamicCommand}: using a map to convert from state to command.
 * The map has to be passed in construction and shouldn't be changed after that.
 * @param <C> state
 */
public abstract class MapChoiceCommand<C> extends DynamicCommand {
    private Map<C, GBCommand> m_commandMap;

    protected MapChoiceCommand(Map<C, GBCommand> commandMap) {
        m_commandMap = commandMap;
    }

    @Override
    protected final GBCommand pick() {
        var opt = state();
        if (!m_commandMap.containsKey(opt)) {
            logger.warn("Invalid option: " + opt);
            return null;
        } else {
            logger.debug("current state: " + opt);
        }
        return m_commandMap.get(opt);
    }

    protected abstract C state();
}

package edu.greenblitz.utils.command.dynamic;

import edu.wpi.first.wpilibj.command.Command;

import java.util.Map;

public abstract class MapChoiceCommand<C> extends MultiChoiceCommand {
    private Map<C, Command> m_commandMap;

    protected MapChoiceCommand(Map<C, Command> commandMap) {
        m_commandMap = commandMap;
    }

    @Override
    protected final Command pick() {
        var opt = state();
        if (!m_commandMap.containsKey(opt)) {
            System.err.println("Invalid option: " + opt);
            return null;
        }
        return m_commandMap.get(opt);
    }

    protected abstract C state();
}

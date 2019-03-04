package edu.greenblitz.utils.command.dynamic;

import edu.greenblitz.utils.command.GBCommand;

import java.util.Map;

public abstract class EnumeratedCommand<E extends Enum<E>> extends MapChoiceCommand<E> {
    public EnumeratedCommand(Map<E, GBCommand> eCommandMap) {
        super(eCommandMap);
    }
}

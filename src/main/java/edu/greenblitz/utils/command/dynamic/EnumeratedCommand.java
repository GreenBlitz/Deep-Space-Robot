package edu.greenblitz.utils.command.dynamic;

import edu.wpi.first.wpilibj.command.Command;

import java.util.Map;

public abstract class EnumeratedCommand<E extends Enum<E>> extends MapChoiceCommand<E> {
    public EnumeratedCommand(Map<E, Command> eCommandMap) {
        super(eCommandMap);
    }
}

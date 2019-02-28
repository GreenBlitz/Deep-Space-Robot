package edu.greenblitz.utils.command.dynamic;

import com.google.common.collect.ImmutableMap;
import edu.greenblitz.utils.command.GBCommand;

public abstract class BinaryChoiceCommand extends MapChoiceCommand<Boolean> {
    public BinaryChoiceCommand(GBCommand onFalse, GBCommand onTrue) {
        super(ImmutableMap.<Boolean, GBCommand>builder().put(false, onFalse).put(true, onTrue).build());
    }
}

package edu.greenblitz.utils.command.dynamic;

import com.google.common.collect.ImmutableMap;
import edu.wpi.first.wpilibj.command.Command;

public abstract class BinaryChoiceCommand extends MapChoiceCommand<Boolean> {
    public BinaryChoiceCommand(Command onFalse, Command onTrue) {
        super(ImmutableMap.<Boolean, Command>builder().put(false, onFalse).put(true, onTrue).build());
    }
}

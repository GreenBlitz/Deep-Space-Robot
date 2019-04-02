package edu.greenblitz.knockdown.commands;

import edu.greenblitz.knockdown.OI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class OIModeConditionCommand extends ConditionalCommand {
    public OIModeConditionCommand(Command onHatch) {
        super(onHatch);
    }

    public OIModeConditionCommand(Command onHatch, Command onCargo) {
        super(onHatch, onCargo);
    }

    public OIModeConditionCommand(String name, Command onHatch) {
        super(name, onHatch);
    }

    public OIModeConditionCommand(String name, Command onHatch, Command onCargo) {
        super(name, onHatch, onCargo);
    }

    @Override
    protected boolean condition() {
        return OI.isStateHatch();
    }
}

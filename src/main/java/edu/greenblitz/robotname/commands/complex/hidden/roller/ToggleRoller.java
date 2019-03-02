package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;

public class ToggleRoller extends DynamicCommand {
    @Override
    protected GBCommand pick() {
        return Roller.getInstance().isExtended() ? new EnsureRollerRetracted() : new EnsureRollerExtended();
    }


}

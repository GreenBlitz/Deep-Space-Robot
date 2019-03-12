package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.wpi.first.wpilibj.command.Command;

public class SafeToggleRoller extends DynamicCommand {
    @Override
    protected Command pick() {
        return Roller.getInstance().isExtended() ? new EnsureRollerRetracted() : new EnsureRollerExtended();
    }
}

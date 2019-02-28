package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleRoller extends DynamicCommand {
    @Override
    protected GBCommand pick() {
        return Roller.getInstance().isExtended() ? new RetractRoller() : new ExtendRoller();
    }
}

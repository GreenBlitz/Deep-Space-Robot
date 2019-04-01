package edu.greenblitz.robotname.commands.complex.roller;

import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class ToggleRoller extends ConditionalCommand {
    public ToggleRoller() {
        super(new RetractRoller(), new ExtendRoller());
    }

    @Override
    protected boolean condition() {
        return Roller.getInstance().isExtended();
    }
}

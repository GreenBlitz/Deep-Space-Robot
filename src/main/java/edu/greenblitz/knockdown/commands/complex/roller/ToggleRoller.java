package edu.greenblitz.knockdown.commands.complex.roller;

import edu.greenblitz.knockdown.commands.simple.roller.ExtendRoller;
import edu.greenblitz.knockdown.commands.simple.roller.RetractRoller;
import edu.greenblitz.knockdown.subsystems.Roller;
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

package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.subsystems.Shifter;

/**
 * This command switches the Gear from the state it is currently in.
 * This command uses the Shifter subsystem.
 * The command will stop as soon as the shift is switched.
 */

public class ToggleShift extends SubsystemCommand<Shifter> {

    public ToggleShift() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
        system.toggleShift();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

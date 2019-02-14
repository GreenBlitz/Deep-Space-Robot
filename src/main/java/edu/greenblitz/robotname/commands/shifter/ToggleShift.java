package edu.greenblitz.robotname.commands.shifter;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Shifter;

/**
 * This command switches the ShifterState from the state it is currently in.
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

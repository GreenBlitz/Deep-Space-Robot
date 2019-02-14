package edu.greenblitz.robotname.commands.shifter;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Shifter;

/**
 * This command switches the ShifterState from the state it is currently in.
 * This command uses the Shifter subsystem.
 * The command will stop as soon as the shift is switched.
 */

public class SwitchShift extends SubsystemCommand<Shifter> {

    public SwitchShift() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
        system.setShift(system.getCurrentShift() == Shifter.ShifterState.POWER ?
                Shifter.ShifterState.SPEED : Shifter.ShifterState.POWER);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

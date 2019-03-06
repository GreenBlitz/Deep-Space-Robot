package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;

/**
 * This command switches the Gear from the state it is currently in.
 * This command uses the Shifter subsystem.
 * The command will stopRolling as soon as the shift is switched.
 */

public class ToggleShift extends ShifterBaseCommand {

    @Override
    protected void execute() {
        system.setShift(system.getCurrentShift() == Shifter.Gear.POWER ? Shifter.Gear.SPEED : Shifter.Gear.POWER);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

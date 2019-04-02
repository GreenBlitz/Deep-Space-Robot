package edu.greenblitz.knockdown.commands.simple.shifter;

import edu.greenblitz.knockdown.subsystems.Shifter;

/**
 * This command switches the Gear from the state it is currently in.
 * This command uses the Shifter subsystem.
 * The command will stopRolling as soon as the shift is switched.
 */

public class ToggleShift extends ShifterBaseCommand {

    @Override
    protected void execute() {
        system.setShift(system.getCurrentGear() == Shifter.Gear.POWER ? Shifter.Gear.SPEED : Shifter.Gear.POWER);
    }

    protected void atEnd(){
        new KeepShift().start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

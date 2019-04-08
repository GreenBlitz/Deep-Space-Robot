package edu.greenblitz.knockdown.commands.complex.climber;

import edu.greenblitz.knockdown.commands.simple.climber.ClimberBigControlByJoystick;
import edu.greenblitz.knockdown.commands.simple.climber.ClimberPExtenderControl;
import edu.greenblitz.knockdown.commands.simple.climber.ClimberProportionalExtendByJoystick;
import edu.greenblitz.knockdown.commands.simple.roller.RollerDoNothing;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ClimbByJoystick extends CommandChain {

    private static final double SAFE_POWER = 1.0;

    public ClimbByJoystick(SmartJoystick bigJoystick, SmartJoystick extenderJoystick, SmartJoystick driveJoystick) {
        addParallel(new RollerDoNothing());
        addParallel(new ClimberBigControlByJoystick(SAFE_POWER, bigJoystick));
        addParallel(new ClimberPExtenderControl(extenderJoystick));
        addParallel(new StartSideClimberControl(driveJoystick));
        addParallel(new ToPower());
    }
}

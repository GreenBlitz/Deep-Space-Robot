package edu.greenblitz.robotname.commands.complex.climber;

import edu.greenblitz.robotname.commands.simple.climber.ClimberBigControlByJoystick;
import edu.greenblitz.robotname.commands.simple.climber.ClimberProportionalExtendByJoystick;
import edu.greenblitz.robotname.commands.simple.roller.RollerDoNothing;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ClimbByJoystick extends CommandChain {

    private static final double SAFE_POWER = 1.0;

    public ClimbByJoystick(SmartJoystick bigJoystick, SmartJoystick extenderJoystick, SmartJoystick driveJoystick) {
        addParallel(new RollerDoNothing());
        addParallel(new ClimberBigControlByJoystick(SAFE_POWER, bigJoystick));
        addParallel(new ClimberProportionalExtendByJoystick(extenderJoystick));
        addParallel(new StartSideClimberControl(driveJoystick));
        addParallel(new ToPower());
    }
}
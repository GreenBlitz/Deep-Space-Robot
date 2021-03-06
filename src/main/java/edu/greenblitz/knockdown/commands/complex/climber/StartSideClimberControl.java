package edu.greenblitz.knockdown.commands.complex.climber;

import edu.greenblitz.knockdown.commands.simple.chassis.driver.SlowArcadeDriveByJoystick;
import edu.greenblitz.knockdown.commands.simple.climber.ClimberDriveByJoystick;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;

public class StartSideClimberControl extends CommandChain {
    private static final double DRIVE_MULT = 0.15;

    public StartSideClimberControl(SmartJoystick joystick) {
        addParallel(new ClimberDriveByJoystick(joystick),
                    new SlowArcadeDriveByJoystick(joystick, DRIVE_MULT));
    }
}

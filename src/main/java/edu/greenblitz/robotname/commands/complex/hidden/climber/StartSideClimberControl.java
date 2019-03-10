package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.commands.simple.chassis.driver.SlowArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.climber.ClimberDriveByJoystick;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;

public class StartSideClimberControl extends CommandChain {
    private static final double DRIVE_MULT = 0.15;

    private SmartJoystick m_joystick;

    public StartSideClimberControl(SmartJoystick joystick) {
        this.m_joystick = joystick;
    }

    @Override
    protected void initChain() {
        addParallel(new ClimberDriveByJoystick(m_joystick),
                new SlowArcadeDriveByJoystick(m_joystick, DRIVE_MULT));
    }
}

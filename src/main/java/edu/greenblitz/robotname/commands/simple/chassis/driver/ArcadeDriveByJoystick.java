package edu.greenblitz.robotname.commands.simple.chassis.driver;

import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ArcadeDriveByJoystick extends ChassisBaseCommand {

    public static final double SPEED_MULT = 1;
    public static final double TURN_MULT = 1;
    public static final double RAMPING_RATE_JOYSTIK = 1;

    private SmartJoystick m_joystick;

    public ArcadeDriveByJoystick(SmartJoystick joystick) {
        m_joystick = joystick;
    }

    @Override
    protected void atInit() {
        m_joystick.rumble(false, 0);
        m_joystick.rumble(true, 0);
    }

    @Override
    protected void execute() {
        Chassis.getInstance().arcadeDrive(SmartJoystick.Axis.LEFT_Y.getValue(m_joystick) * SPEED_MULT,
                SmartJoystick.Axis.RIGHT_X.getValue(m_joystick) * TURN_MULT);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void atEnd() {
//        Chassis.getInstance().setRampRate(0);
        Chassis.getInstance().stop();
    }
}
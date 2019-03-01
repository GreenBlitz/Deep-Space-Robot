package edu.greenblitz.robotname.commands.simple.chassis.driver;

import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.hid.SmartJoystick;

public class TankDriveByJoytick extends ChassisBaseCommand {

    private SmartJoystick m_joystick;

    public TankDriveByJoytick(SmartJoystick joystick) {
        m_joystick = joystick;
    }

    @Override
    protected void execute() {
        Chassis.getInstance().tankDrive(SmartJoystick.Axis.LEFT_Y.getValue(m_joystick),
                SmartJoystick.Axis.RIGHT_Y.getValue(m_joystick));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        super.end();
        Chassis.getInstance().stop();
    }
}
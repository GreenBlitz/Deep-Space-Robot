package edu.greenblitz.robotname.commands.simple.chassis.driver;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.base.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class SlowArcadeDriveByJoystick extends JoystickCommand<Chassis> {

    private double m_driveMulti;

    public SlowArcadeDriveByJoystick(SmartJoystick joystick, double driveMulti) {
        super(Chassis.getInstance(), joystick);
        m_driveMulti = driveMulti;
    }

    @Override
    protected void execute() {
        Chassis.getInstance().arcadeDrive(-SmartJoystick.Axis.LEFT_Y.getValue(joystick) * m_driveMulti,
                                         SmartJoystick.Axis.RIGHT_X.getValue(joystick) * m_driveMulti);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

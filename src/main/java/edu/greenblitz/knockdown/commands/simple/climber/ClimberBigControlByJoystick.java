package edu.greenblitz.knockdown.commands.simple.climber;

import edu.greenblitz.knockdown.subsystems.Climber;
import edu.greenblitz.utils.command.base.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ClimberBigControlByJoystick extends JoystickCommand<Climber.Big> {
    /**
     * unsafe power = power near control board
     * safe power = power away from control board
     */
    private double safePower;

    public ClimberBigControlByJoystick(double safePower, SmartJoystick miseryStick) {
        super(Climber.getInstance().getBig(), miseryStick);
        this.safePower = safePower;
    }

    @Override
    protected void execute() {
            system.set(-SmartJoystick.Axis.RIGHT_Y.getValue(joystick) * safePower);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
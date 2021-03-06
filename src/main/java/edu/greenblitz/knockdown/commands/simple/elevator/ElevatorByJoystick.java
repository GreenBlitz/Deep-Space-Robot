package edu.greenblitz.knockdown.commands.simple.elevator;

import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.base.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ElevatorByJoystick extends JoystickCommand<Elevator> {
    public ElevatorByJoystick(SmartJoystick joystick) {
        super(Elevator.getInstance(), joystick);
    }

    @Override
    protected void execute() {
        Elevator.getInstance().setRawPower(SmartJoystick.Axis.LEFT_TRIGGER.getValue(joystick) -
                                            SmartJoystick.Axis.RIGHT_TRIGGER.getValue(joystick));
    }


    @Override
    protected boolean isFinished() {
        return false;
    }
}
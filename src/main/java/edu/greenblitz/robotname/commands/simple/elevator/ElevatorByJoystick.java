package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

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
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
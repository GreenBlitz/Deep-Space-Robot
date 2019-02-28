package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class RawElevatorByJoystick extends SubsystemCommand<Elevator> {

    public RawElevatorByJoystick() {
        super(Elevator.getInstance());
    }

    @Override
    protected void execute() {
        system.setRawPower(0.5 * OI.getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

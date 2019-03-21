package edu.greenblitz.utils.command.base;

import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.hid.SmartJoystick;

public abstract class JoystickCommand<S extends GBSubsystem> extends SubsystemCommand<S> {
    protected SmartJoystick joystick;

    public JoystickCommand(S system, SmartJoystick joystick) {
        super(system);
        this.joystick = joystick;
    }
}

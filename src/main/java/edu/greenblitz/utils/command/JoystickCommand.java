package edu.greenblitz.utils.command;

import edu.greenblitz.utils.SmartJoystick;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class JoystickCommand<S extends Subsystem> extends SubsystemCommand<S> {
    protected SmartJoystick joystick;

    public JoystickCommand(S system, SmartJoystick joystick) {
        super(system);
        this.joystick = joystick;
    }
}

package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ClimberBigControlByJoystick extends JoystickCommand<Climber.Big> {
    /**
     * unsafe power = power near control board
     * safe power = power away from control board
     */
    private double unsafePower, safePower;

    public ClimberBigControlByJoystick(double unsafePower, double safePower, SmartJoystick miseryStick) {
        super(Climber.getInstance().getBig(), miseryStick);
        this.safePower = safePower;
        this.unsafePower = Math.max(Math.min(0.05, unsafePower), 0);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
        if (system.isAtLimit())
            system.lower(Math.max(0, SmartJoystick.Axis.RIGHT_Y.getValue(joystick) * unsafePower));
        else
            system.lower(SmartJoystick.Axis.RIGHT_Y.getValue(joystick) * safePower);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

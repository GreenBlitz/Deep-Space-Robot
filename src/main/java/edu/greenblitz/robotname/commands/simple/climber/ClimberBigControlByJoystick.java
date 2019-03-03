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
    private double safePower;

    public ClimberBigControlByJoystick(double safePower, SmartJoystick miseryStick) {
        super(Climber.getInstance().getBig(), miseryStick);
        this.safePower = safePower;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
            system.lower(SmartJoystick.Axis.RIGHT_Y.getValue(joystick) * safePower);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.base.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ClimberBigControlByTwoAxis extends JoystickCommand<Climber.Big> {

    public ClimberBigControlByTwoAxis(Climber.Big system, SmartJoystick joystick) {
        super(system, joystick);
    }

    @Override
    public void execute(){
        if(joystick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER) > 0)
            Climber.getInstance().getBig().lower(joystick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER));
        else
            Climber.getInstance().getBig().higher(joystick.getAxisValue(SmartJoystick.Axis.LEFT_TRIGGER));
    }

    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

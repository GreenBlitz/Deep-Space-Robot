package edu.greenblitz.knockdown.commands.simple.climber;

import edu.greenblitz.knockdown.subsystems.Climber;
import edu.greenblitz.utils.command.base.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

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

    @Override
    protected boolean isFinished() {
        return false;
    }
}

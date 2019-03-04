package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ClimberExtendByJoystick extends JoystickCommand<Climber.Extender> {
    public ClimberExtendByJoystick(SmartJoystick joystick) {
        super(Climber.getInstance().getExtender(), joystick);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute(){
        system.extend(SmartJoystick.Axis.RIGHT_TRIGGER.getValue(joystick) - SmartJoystick.Axis.LEFT_TRIGGER.getValue(joystick));
    }
}

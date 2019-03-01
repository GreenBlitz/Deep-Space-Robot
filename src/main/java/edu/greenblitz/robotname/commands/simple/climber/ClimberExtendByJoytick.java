package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Optional;

public class ClimberExtendByJoytick extends JoystickCommand<Climber.Extender> {
    public ClimberExtendByJoytick(SmartJoystick joystick) {
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
        double rtValue = SmartJoystick.Axis.RIGHT_TRIGGER.getValue(joystick);
        double ltValue = SmartJoystick.Axis.LEFT_TRIGGER.getValue(joystick);
        if (rtValue < 0.01)
            system.extend(-ltValue);
        else
            system.extend(rtValue);
    }
}

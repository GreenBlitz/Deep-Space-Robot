package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.commands.simple.climber.ClimberBigControlByJoystick;
import edu.greenblitz.robotname.commands.simple.climber.ClimberDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.climber.ClimberExtendByJoytick;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ClimbByJoystick extends CommandChain {

    private static SmartJoystick joystick = null;

    @Override
    protected void initChain() {
        addParallel(new ClimberBigControlByJoystick(0.05, 0.2, joystick),
                new ClimberExtendByJoytick(joystick),
                new ClimberDriveByJoystick(joystick));
    }
}

package edu.greenblitz.knockdown.commands.complex.climber;

import edu.greenblitz.knockdown.commands.simple.climber.ClimberBigControlByJoystick;
import edu.greenblitz.knockdown.commands.simple.climber.ClimberProportionalExtendByJoystick;
import edu.greenblitz.knockdown.commands.simple.roller.RollerDoNothing;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ClimbByJoystickRestricted extends CommandChain {

    private SmartJoystick m_bigJoystick, m_extenderJoystick, m_driveJoystick;
    private static final double SAFE_POWER = 1.0;

    public ClimbByJoystickRestricted(SmartJoystick bigJoystick, SmartJoystick extenderJoystick, SmartJoystick driveJoystick) {
        m_bigJoystick = bigJoystick;
        m_extenderJoystick = extenderJoystick;
        m_driveJoystick = driveJoystick;

        addParallel(new ClimberBigControlByJoystick(SAFE_POWER, m_bigJoystick),
                new ClimberProportionalExtendByJoystick(m_extenderJoystick),
                new StopSideClimberControl(),
                new ToPower(),
                new RollerDoNothing());
    }

    @Override
    protected void atEnd() {
        new ClimbByJoystick(m_bigJoystick, m_extenderJoystick, m_driveJoystick).start();
    }
}

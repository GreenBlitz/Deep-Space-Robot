package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.commands.simple.climber.ClimberBigControlByJoystick;
import edu.greenblitz.robotname.commands.simple.climber.ClimberExtendByJoystick;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;

import java.util.Set;

public class ClimbByJoystick extends CommandChain {

    private SmartJoystick m_bigJoystick, m_extenderJoystick, m_driveJoystick;
    private static final double SAFE_POWER = 1.0;

    public ClimbByJoystick(SmartJoystick bigJoystick, SmartJoystick extenderJoystick, SmartJoystick driveJoystick) {
        m_bigJoystick = bigJoystick;
        m_extenderJoystick = extenderJoystick;
        m_driveJoystick = driveJoystick;
    }

    @Override
    protected void initChain() {
        addParallel(new ClimberBigControlByJoystick(SAFE_POWER, m_bigJoystick),
                    new ClimberExtendByJoystick(m_extenderJoystick),
                    new StartSideClimberControl(m_driveJoystick),
                    new ToPower());
    }

    @Override
    public Set<GBSubsystem> getLazyRequirements() {
        return Set.of(Chassis.getInstance(), Climber.getInstance().getBig(), Climber.getInstance().getExtender(), Climber.getInstance().getWheels());
    }
}

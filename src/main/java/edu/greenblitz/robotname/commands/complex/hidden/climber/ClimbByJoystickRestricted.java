package edu.greenblitz.robotname.commands.complex.hidden.climber;

import edu.greenblitz.robotname.commands.simple.climber.ClimberBigControlByJoystick;
import edu.greenblitz.robotname.commands.simple.climber.ClimberExtendByJoystick;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Set;

public class ClimbByJoystickRestricted extends CommandChain {

    private SmartJoystick m_bigJoystick, m_extenderJoystick, m_driveJoystick;
    private static final double SAFE_POWER = 1.0;

    public ClimbByJoystickRestricted(SmartJoystick bigJoystick, SmartJoystick extenderJoystick, SmartJoystick driveJoystick) {
        m_bigJoystick = bigJoystick;
        m_extenderJoystick = extenderJoystick;
        m_driveJoystick = driveJoystick;
    }

    @Override
    protected void initChain() {
        addParallel(new ClimberBigControlByJoystick(SAFE_POWER, m_bigJoystick),
                new ClimberExtendByJoystick(m_extenderJoystick),
                new StopSideClimberControl(),
                new ToPower());
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return Set.of(Chassis.getInstance(), Climber.getInstance().getBig(), Climber.getInstance().getExtender(), Climber.getInstance().getWheels());
    }

    @Override
    protected void atEnd() {
        new ClimbByJoystick(m_bigJoystick, m_extenderJoystick, m_driveJoystick).start();
    }
}

package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.base.Position;

import java.util.Optional;


public class SetLocalizerLocation extends GBCommand {

    private double m_x, m_y, m_a;

    public SetLocalizerLocation(double x, double y, double a) {
        m_x = x;
        m_y = y;
        m_a = a;
    }

    public SetLocalizerLocation(Position location) {
        this(location.getX(), location.getY(), location.getAngle());
    }

    @Override
    protected void initialize() {
        if (m_x == Double.NaN) m_x = Chassis.getInstance().getLocation().getX();
        if (m_y == Double.NaN) m_y = Chassis.getInstance().getLocation().getY();
        if (m_a == Double.NaN) m_a = Chassis.getInstance().getLocation().getAngle();
        Chassis.getInstance().setLocation(new Position(m_x, m_y, m_a));
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
}

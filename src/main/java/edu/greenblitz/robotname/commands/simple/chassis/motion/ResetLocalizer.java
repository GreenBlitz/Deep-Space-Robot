package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.base.Position;

import java.util.Optional;


public class ResetLocalizer extends GBCommand {

    private double m_x, m_y, m_angle;

    public ResetLocalizer(double x, double y, double angle) {
        m_x = x;
        m_y = y;
        m_angle = angle;
    }

    public ResetLocalizer(Position location) {
        this(location.getX(), location.getY(), location.getAngle());
    }

    public ResetLocalizer() {
        this(0, 0 , 0);
    }

    @Override
    protected void atInit() {
        Chassis.getInstance().setLocation(new Position(m_x, m_y, m_angle));
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

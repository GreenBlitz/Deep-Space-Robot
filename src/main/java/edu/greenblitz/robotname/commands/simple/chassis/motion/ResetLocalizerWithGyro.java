package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.base.Position;

import java.util.Optional;


public class ResetLocalizerWithGyro extends GBCommand {

    private double m_x, m_y;

    public ResetLocalizerWithGyro(double x, double y) {
        m_x = x;
        m_y = y;
    }

    public ResetLocalizerWithGyro(Position location) {
        this(location.getX(), location.getY());
    }

    public ResetLocalizerWithGyro() {
        this(0, 0);
    }

    @Override
    protected void atInit() {
        Chassis.getInstance().setLocation(
                new Position(m_x, m_y, -Math.toRadians(Chassis.getInstance().getAngle())));
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


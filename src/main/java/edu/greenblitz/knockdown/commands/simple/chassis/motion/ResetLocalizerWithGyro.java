package edu.greenblitz.knockdown.commands.simple.chassis.motion;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.base.GBCommand;
import org.greenblitz.motion.base.Position;


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
                new Position(m_x, m_y, -Math.toRadians(Chassis.getInstance().getAngle()) - Chassis.getInstance().getGyroZero()));
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}


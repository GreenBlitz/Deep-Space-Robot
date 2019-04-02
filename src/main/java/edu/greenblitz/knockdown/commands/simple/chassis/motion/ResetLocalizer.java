package edu.greenblitz.knockdown.commands.simple.chassis.motion;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.base.GBCommand;
import org.greenblitz.motion.base.Position;


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

}

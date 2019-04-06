package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class WaitLocalizerPassedY extends Command {
    private double m_x;
    private double m_y;

    public WaitLocalizerPassedY(double x, double y) {
        m_x = x;
        m_y = y;
    }

    @Override
    protected boolean isFinished() {
        return passedX() && passedY();
    }

    private boolean passedX() {
        // It's lower than because motions axis system is stupid.
        return Chassis.getInstance().getLocation().getX() <= m_x;
    }

    private boolean passedY() {
        return Chassis.getInstance().getLocation().getY() >= m_y;
    }
}

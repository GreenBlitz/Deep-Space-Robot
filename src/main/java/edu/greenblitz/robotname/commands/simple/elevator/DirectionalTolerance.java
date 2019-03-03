package edu.greenblitz.robotname.commands.simple.elevator;

import org.greenblitz.motion.tolerance.ITolerance;

public class DirectionalTolerance implements ITolerance {
    /**
     * Tolerance will be onTarget iff current will be grater or equal to goal
     * @return bigger-than tolerance
     */
    public static DirectionalTolerance biggerThan() {
        return BIGGER_THAN;
    }

    /**
     * Tolerance will be onTarget iff current will be smaller or equal to goal
     * @return smaller-than tolerance
     */
    public static DirectionalTolerance smallerThan() {
        return SMALLER_THAN;
    }

    private static DirectionalTolerance BIGGER_THAN = new DirectionalTolerance(true),
            SMALLER_THAN = new DirectionalTolerance(false);

    private boolean m_direction;

    private DirectionalTolerance(boolean direction) {
        m_direction = direction;
    }

    @Override
    public boolean onTarget(double goal, double current) {
        var err = current - goal;

        if (m_direction) return err >= 0;
        else return err <= 0;
    }
}

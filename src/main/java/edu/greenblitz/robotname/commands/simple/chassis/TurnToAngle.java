package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.subsystems.Chassis;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class TurnToAngle extends ChassisBaseCommand {

    private static final PIDObject PID_CONFIG = new PIDObject(0.8, 0, 0);
    private static final double MINIMUM_OUTPUT = 0.35;
    private static final ITolerance PID_TOLERANCE = new AbsoluteTolerance(5 / 90.0);

    private PIDController m_controller;
    private int m_onTarget = 0;
    private double targetAngle;

    public TurnToAngle(double angle) {
        m_controller = new PIDController(PID_CONFIG, PID_TOLERANCE);
        targetAngle = angle;
    }

    protected void initialize() {
        Chassis.getInstance().resetNavx();
        m_controller.configureOutputLimits(-0.8, 0.8);
        m_controller.setGoal(targetAngle / 90.0);
    }

    protected boolean isFinished() {
        if (m_controller.isFinished())
            m_onTarget++;
        else
            m_onTarget = 0;
        return m_onTarget >= 4;
    }

    @Override
    protected void execute() {
        set(m_controller.calculatePID(get()));
    }

    private double angleToPID(double angle) {
        return angle / 90;
    }

    private double get() {
        return angleToPID(system.getAngle());
    }

    private void set(double turn) {
        system.arcadeDrive(0, turn);
    }
}
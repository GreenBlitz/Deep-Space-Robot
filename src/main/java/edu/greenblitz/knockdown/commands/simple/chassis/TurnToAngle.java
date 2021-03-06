package edu.greenblitz.knockdown.commands.simple.chassis;

import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class TurnToAngle extends ChassisBaseCommand {

    private static final double SLOWDOWN_ANGLE = 10;
    private static final double POWER_AT_SLOWDOWN = 0.8;
    private static final PIDObject PID_CONFIG = new PIDObject(POWER_AT_SLOWDOWN / SLOWDOWN_ANGLE, 0, 0);
    private static final double FULL_POWER = 0.8;
    private static final double DEADBAND = 0;
    private static final ITolerance PID_TOLERANCE = new AbsoluteTolerance(2);

    private PIDController m_controller;
    private int m_onTarget = 0;
    private double targetAngle;

    public TurnToAngle(double angle) {
        m_controller = new PIDController(PID_CONFIG, PID_TOLERANCE);
        targetAngle = angle;
    }

    protected void atInit() {
        m_controller.configure(get(), 0, -FULL_POWER, FULL_POWER, DEADBAND);
    }

    protected boolean isFinished() {
        if (m_controller.isFinished(get()))
            m_onTarget++;
        else
            m_onTarget = 0;
        return m_onTarget >= 4;
    }

    @Override
    protected void execute() {
        set(m_controller.calculatePID(get()));
    }

    private double get() {
        return system.getAngle();
    }

    private void set(double turn) {
        system.arcadeDrive(0, turn);
    }
}
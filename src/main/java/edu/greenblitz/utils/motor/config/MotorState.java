package edu.greenblitz.utils.motor.config;

public enum MotorState {
    RAW(Target.PERCENT, false),
    POSITION(Target.POSITION, true),
    VELOCITY(Target.VELOCITY, true),
    CURRENT(Target.CURRENT, true),
    VOLTAGE(Target.VOLTAGE, true),
    FOLLOW(Target.MOTOR, false),
    SMART_PROFILING(Target.POSITION, false),
    DISABLED(null, false);

    private final Target m_target;
    private final boolean m_isClosedLoop;

    MotorState(Target target, boolean isClosedLoop) {
        this.m_target = target;
        this.m_isClosedLoop = isClosedLoop;
    }

    public Target getTarget() {
        return m_target;
    }

    public boolean isClosedLoop() {
        return m_isClosedLoop;
    }

    public boolean isEnabled() {
        return m_target != null;
    }
}

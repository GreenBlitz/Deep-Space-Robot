package edu.greenblitz.knockdown.commands.simple.chassis;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.base.TimedSubsystemCommand;

public class DriveByTime extends TimedSubsystemCommand<Chassis> {
    private static final double DEFAULT_POWER = 0.5;
    private static final boolean DEFAULT_BACKWARDS = true;
    private static final long DEFAULT_FALL_TIME = 500;

    private final double m_power;
    private final int m_backwards;

    public DriveByTime(long ms, double power, boolean isBackwards) {
        super(ms, Chassis.getInstance());
        m_power = power;
        m_backwards = isBackwards ? -1 : 1;
    }

    public DriveByTime() {
        this(DEFAULT_FALL_TIME, DEFAULT_POWER, DEFAULT_BACKWARDS);
    }

    @Override
    protected void atInit() {
        system.arcadeDrive(m_power * m_backwards, 0);
    }

}

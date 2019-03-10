package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class DropFromHeight extends TimedSubsystemCommand<Chassis> {
    private static final double DOWN_ACCELERATION_THRESHOLD = 0.05;
    private static final double DEFAULT_POWER = 0.2;
    private static final boolean DEFAULT_BACKWARDS = true;

    private final double m_power;
    private final int m_backwards;

    public DropFromHeight(long ms, double power, boolean isBackwards) {
        super(ms, Chassis.getInstance());
        m_power = power;
        m_backwards = isBackwards ? -1 : 1;
    }

    public DropFromHeight(long ms) {
        this(ms, DEFAULT_POWER, DEFAULT_BACKWARDS);
    }

    @Override
    protected void initialize() {
        system.arcadeDrive(m_power * m_backwards, 0);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(system.getDownAcceleration()) < DOWN_ACCELERATION_THRESHOLD || isTimedOut();
    }
}

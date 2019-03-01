package edu.greenblitz.utils.command;

import edu.greenblitz.robotname.data.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Optional;

/**
 * Holds execution in command groups until a certain subsystem is clear to require.
 * <p>
 * WARNING: DO NOT USE THIS ON A DEFAULT COMMAND
 * </p>
 */
public class WaitUntilClear extends GBCommand {
    private Subsystem m_subsystem;

    public WaitUntilClear(Subsystem subsystem) {
        m_subsystem = subsystem;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return m_subsystem.getCurrentCommand().equals(m_subsystem.getDefaultCommand());
    }
}

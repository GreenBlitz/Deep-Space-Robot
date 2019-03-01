package edu.greenblitz.utils.command;

import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Holds execution in command groups until a certain m_commands finishes.
 * <p>
 *     WARNING: DO NOT USE THIS ON A DEFAULT COMMAND
 * </p>
 */
public class WaitUntilClear extends GBCommand {
    private Subsystem m_subsystem;

    public WaitUntilClear(Subsystem subsystem) {
        m_subsystem = subsystem;
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, null, null);
    }

    @Override
    protected boolean isFinished() {
        return m_subsystem.getCurrentCommand().equals(m_subsystem.getDefaultCommand());
    }
}

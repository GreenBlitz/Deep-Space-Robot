package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Holds execution in command groups until a certain commands finishes.
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
    protected boolean isFinished() {
        return m_subsystem.getCurrentCommand().equals(m_subsystem.getDefaultCommand());
    }
}

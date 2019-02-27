package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DynamicRequire extends Command {
    private Subsystem m_subsystem;

    public DynamicRequire(Subsystem subsystem) {
        m_subsystem = subsystem;
    }

    @Override
    protected void initialize() {
        requires(m_subsystem);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

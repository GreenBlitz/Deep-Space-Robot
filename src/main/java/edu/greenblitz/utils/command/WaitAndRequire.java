package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Set;

public class WaitAndRequire extends GBCommand {
    private Subsystem m_subsystem;

    public WaitAndRequire(Subsystem subsystems) {
        super(WaitAndRequire.class.getSimpleName() + " for " + subsystems);
        m_subsystem = subsystems;
    }

    @Override
    protected boolean isFinished() {
        var defaultCmd = m_subsystem.getDefaultCommand();
        var currentCmd = m_subsystem.getCurrentCommand();

        if (defaultCmd == null) return currentCmd == null;
        if (currentCmd == null) return false;

        return defaultCmd.getName().equals(currentCmd.getName());
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return Set.of(m_subsystem);
    }
}

package edu.greenblitz.utils.command;

import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Optional;
import java.util.Set;

public class WaitAndRequire extends GBCommand {
    private GBSubsystem m_subsystem;

    public WaitAndRequire(GBSubsystem subsystems) {
        super(WaitAndRequire.class.getSimpleName() + " for " + subsystems);
        m_subsystem = subsystems;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
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
    public Set<GBSubsystem> getLazyRequirements() {
        return Set.of(m_subsystem);
    }
}

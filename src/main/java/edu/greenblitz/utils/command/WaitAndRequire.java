package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Collections;
import java.util.List;

public class WaitAndRequire extends GBCommand {
    private Subsystem m_subsystem;

    public WaitAndRequire(Subsystem subsystem) {
        m_subsystem = subsystem;
    }

    @Override
    protected boolean isFinished() {
        return m_subsystem.getCurrentCommand().equals(m_subsystem.getDefaultCommand());
    }

    @Override
    public List<Subsystem> getRequirements() {
        return Collections.singletonList(m_subsystem);
    }
}

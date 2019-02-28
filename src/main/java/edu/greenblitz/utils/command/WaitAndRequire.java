package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Arrays;
import java.util.Set;

public class WaitAndRequire extends GBCommand {
    private Subsystem[] m_subsystem;

    public WaitAndRequire(Subsystem... subsystem) {
        m_subsystem = subsystem;
    }

    @Override
    protected boolean isFinished() {
//        return m_subsystem.getCurrentCommand().equals(m_subsystem.getDefaultCommand());
        return Arrays.stream(m_subsystem).filter(sys -> sys.getDefaultCommand() == sys.getCurrentCommand()).findAny().isEmpty();
    }

    @Override
    public Set<Subsystem> getLazyRequirements() {
        return Set.of(m_subsystem);
    }
}

package edu.greenblitz.utils.command;

import edu.greenblitz.utils.command.base.GBCommand;
import edu.wpi.first.wpilibj.command.Command;

import java.util.Arrays;

public class ExecuteIfPossible extends GBCommand {
    private GBSubsystem[] m_subsystems;
    private Command m_command;
    
    public ExecuteIfPossible(Command command, GBSubsystem... subsystems) {
        super("ExecuteWhenFree: " + command + ", " + Arrays.toString(subsystems));
        m_subsystems = subsystems;
        m_command = command;
    }

    @Override
    protected void atInit() {
        if (Arrays.stream(m_subsystems).allMatch(GBSubsystem::isAtDefaultState)) {
            m_command.start();
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.utils.command.chain.CommandChain;

public class MultiPathAPPCCommand extends CommandChain {

    private APPCCommand[] m_paths;

    public MultiPathAPPCCommand(String name, APPCCommand... paths) {
        super(name);
        m_paths = paths;
    }

    @Override
    protected void initChain() {
        for (APPCCommand path : m_paths)
            addSequential(path);
    }
}

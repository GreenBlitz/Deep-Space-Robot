package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class HandleCompressor extends SubsystemCommand<Pneumatics> {

    public HandleCompressor() {
        super(Pneumatics.getInstance());
    }

    @Override
    protected void atInit() {
        system.setCompressor(false);
    }

    @Override
    protected void execute() {
        if (system.getPressure() < 70) {
            system.setCompressor(true);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
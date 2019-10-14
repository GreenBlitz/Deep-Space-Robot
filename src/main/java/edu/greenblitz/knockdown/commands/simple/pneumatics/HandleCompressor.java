package edu.greenblitz.knockdown.commands.simple.pneumatics;

import edu.greenblitz.knockdown.subsystems.Pneumatics;
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
        if (system.getPressure() < 40) {
            system.setCompressor(true);
        }
        if (system.getPressure() > 60){
            system.setCompressor(false);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
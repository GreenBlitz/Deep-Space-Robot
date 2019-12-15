package edu.greenblitz.knockdown.commands.simple.pneumatics;

import edu.greenblitz.knockdown.subsystems.Pneumatics;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class HandleCompressor extends SubsystemCommand<Pneumatics> {

    public HandleCompressor() {
        super(Pneumatics.getInstance());
    }

    @Override
    protected void atInit() {
        System.out.println("Compressor on");
        system.setCompressor(true);
    }

    @Override
    protected void execute() {
        if (system.getPressure() < 55) {
            system.setCompressor(true);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.chassis.vision.TriggerDriveByVision;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class HybridAlign extends CommandChain {
    public HybridAlign() {
        addSequential(new RetractPoker());
        addSequential(new TriggerDriveByVision());
    }

    @Override
    protected void atEnd() {
        new ConditionalCommand("extend at hatch mode", new ExtendPoker()) {
            @Override
            protected boolean condition() {
                return OI.isStateHatch();
            }
        }.start();
    }
}

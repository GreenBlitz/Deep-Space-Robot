package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.vision.AlignToVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendAndHold;
import edu.greenblitz.utils.command.chain.CommandChain;

public class HybridCollectHatchPanel extends CommandChain {
    public HybridCollectHatchPanel() {
        addParallel(new ExtendAndHold(), new AlignToVisionTarget());
    }
}

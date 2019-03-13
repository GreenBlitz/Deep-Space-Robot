package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.vision.AlignToVisionTarget;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class HybridPlaceHatchPanel extends CommandChain {
    public HybridPlaceHatchPanel() {
        requires(Poker.getInstance());
        addSequential(new AlignToVisionTarget());
    }

    @Override
    protected void atEnd() {
        Poker.getInstance().release();
        Poker.getInstance().retract();
    }
}

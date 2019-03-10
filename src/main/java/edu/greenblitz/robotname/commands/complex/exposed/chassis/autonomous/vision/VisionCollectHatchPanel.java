package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionCollectHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.7;
    private static final double EXTEND_DISTANCE = 0.15;

    @Override
    protected void initChain() {
        addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        addSequential(new ExtendPoker());
        addParallel(new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 1000));
        addParallel(new RetractPoker(), new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 1000));
    }
}
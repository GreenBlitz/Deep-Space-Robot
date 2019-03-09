package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.*;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double DISTANCE = 1.0;

    @Override
    protected void initChain() {
        addParallel(new RetractAndHold(100), new DriveToDistanceFromVisionTarget(DISTANCE));
        addParallel(new ExtendPoker(), new DriveStraightByDistance(DISTANCE - 0.3, 1000));
        addParallel(new ReleaseHatch(), new DriveStraightByDistance(-0.7, 1000));
        addSequential(new RetractAndHold(100));
    }
}
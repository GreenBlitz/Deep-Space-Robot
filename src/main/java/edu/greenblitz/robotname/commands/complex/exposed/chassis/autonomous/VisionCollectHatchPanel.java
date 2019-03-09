package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionCollectHatchPanel extends CommandChain {

    private static final double DISTANCE = 1.1;

    @Override
    protected void initChain() {
        addParallel(new RetractPoker(), new HoldHatch(), new DriveToDistanceFromVisionTarget(DISTANCE));
        addParallel(new ExtendPoker(), new DriveStraightByDistance(DISTANCE-0.1, 1000));
        addParallel(new RetractPoker(), new DriveStraightByDistance(-0.5, 1000));
    }
}
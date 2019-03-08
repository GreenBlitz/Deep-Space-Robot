package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double DISTANCE = 0.4;

    @Override
    protected void initChain() {
        addParallel(new RetractPoker(), new HoldHatch(), new DriveToDistanceFromVisionTarget(DISTANCE));
        addParallel(new ExtendPoker(), new DriveStraightByDistance(DISTANCE, 5000));
        addSequential(new ReleaseHatch());
    }
}
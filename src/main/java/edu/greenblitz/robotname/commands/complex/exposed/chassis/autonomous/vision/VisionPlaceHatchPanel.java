package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.*;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.7;
    private static final double EXTEND_DISTANCE = 0.3;

    @Override
    protected void initChain() {
        addParallel(new RetractAndHold(100), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        addParallel(new ExtendPoker(), new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 1000));
        addParallel(new ReleaseHatch(), new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 1000));
        addSequential(new RetractAndHold(100));
    }
}
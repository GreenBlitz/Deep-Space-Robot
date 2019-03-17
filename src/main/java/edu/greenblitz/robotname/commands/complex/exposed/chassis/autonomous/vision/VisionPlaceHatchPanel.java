package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.complex.exposed.HoldHatchAndMoveToFloor;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 1;
    private static final double EXTEND_DISTANCE = 0.0;

    public VisionPlaceHatchPanel() {
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new Part3());
    }

    @Override
    protected void atEnd() {
        new HoldHatchAndMoveToFloor().start();
    }

    @Override
    protected void atInterrupt() {}

    private class Part1 extends CommandChain {
        private Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        }
    }

    private class Part2 extends CommandChain {
        private Part2() {
            addSequential(new ExtendPoker(50));
            addSequential(new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 1000));
        }
    }

    private class Part3 extends CommandChain {
        private Part3() {
            addParallel(new ReleaseHatch(), new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 1000));
        }
    }
}
package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionCollectHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.8;
    private static final double EXTEND_DISTANCE = -0.1;

    public VisionCollectHatchPanel() {
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new Part3());
    }

    private class Part1 extends CommandChain{
        private Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        }
    }

    private class Part2 extends CommandChain{
        private Part2() {
            addParallel(new ExtendPoker(), new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 1000));
        }
    }

    private class Part3 extends CommandChain {
        private Part3() {
            addParallel(new RetractPoker(), new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 1000));
        }
    }
}
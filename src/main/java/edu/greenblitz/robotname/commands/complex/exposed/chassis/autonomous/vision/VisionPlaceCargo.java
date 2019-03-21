package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionPlaceCargo extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.9;
    private static final double EXTEND_DISTANCE = 0.2;

    public VisionPlaceCargo() {
        addSequential(new Part1());
        addSequential(new Part2());
    }

    @Override
    protected void atEnd() {
//        new SafeMoveElevator(Elevator.Level.GROUND).start();
    }

    @Override
    protected void atInterrupt() {}

    private class Part1 extends CommandChain {
        private Part1() {
            addParallel(new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        }
    }

    private class Part2 extends CommandChain{
        private Part2() {
            addParallel(new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 1000));
        }
    }
}
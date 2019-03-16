package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.*;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.chain.CommandChain;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.8;
    private static final double EXTEND_DISTANCE = 0.1;

    public VisionPlaceHatchPanel() {
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new Part3());
        addSequential(new Part4());
    }

    private static class Part1 extends CommandChain {
        public Part1() {
            addParallel(new RetractPoker(), new HoldHatch(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        }
    }

    private static class Part2 extends CommandChain {
        public Part2() {
            addParallel(new ExtendPoker(), new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 1000));
        }
    }

    private static class Part3 extends CommandChain {
        public Part3() {
            addParallel(new ReleaseHatch(), new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 1000));
        }
    }

    private static class Part4 extends CommandChain {
        public Part4() {
            addParallel(new HoldHatch(), new RetractPoker());
        }
    }
}
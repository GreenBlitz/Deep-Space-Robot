package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.complex.exposed.HoldHatchAndMoveToFloor;
import edu.greenblitz.robotname.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.GearDependentDouble;
import edu.greenblitz.utils.command.CommandChain;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.8; // TODO here it's 0.5, in collect it's 1.1 place works but this doesn't. look into it
    private static final double EXTEND_DISTANCE = 0.0;
    private static final double VISION_TARGET_OFFSET = 5;

    public VisionPlaceHatchPanel() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new Cleanup());
    }

    public static class Part1 extends CommandChain {
        public Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, VISION_TARGET_OFFSET));
        }
    }

    public static class Part2 extends CommandChain {
        public Part2() {
            addSequential(new ExtendPoker(50));
            addSequential(new DriveStraightByDistance((ALIGN_DISTANCE - EXTEND_DISTANCE), 1100)); // was 1000
            addSequential(new ReleaseHatch());
        }
    }

    public static class Cleanup extends CommandChain  {
        public Cleanup() {
            addSequential(new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 600)); // was 600
            addSequential(new HoldHatchAndMoveToFloor());
        }
    }
}
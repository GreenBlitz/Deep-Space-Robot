package edu.greenblitz.robotname.commands.complex.exposed.chassis.vision;

import edu.greenblitz.robotname.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.robotname.commands.simple.chassis.DriveByGyro;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.data.GearDependentDouble;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.command.WaitUntilFree;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0.0;
    private static final double VISION_TARGET_OFFSET = -3;

    public VisionPlaceHatchPanel() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new ReleaseHatch(400));
        addSequential(new Cleanup());
    }

    public static class Part1 extends CommandChain {
        public Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, VISION_TARGET_OFFSET, true));
        }
    }

    public static class Part2 extends CommandChain {
        public Part2() {
            addSequential(new WaitUntilFree(Elevator.getInstance()));
            addSequential(new ExtendPoker(50));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 850,
                    new GearDependentDouble(0.4, 0.4)));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 800,
                    new GearDependentDouble(0.2, 0.2)));
        }
    }

    public static class Cleanup extends CommandChain {
        public Cleanup() {
            addSequential(new DriveByGyro(EXTEND_DISTANCE - ALIGN_DISTANCE, 600)); // was 600
            addSequential(new HoldHatch());
        }
    }
}
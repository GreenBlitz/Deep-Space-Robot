package edu.greenblitz.knockdown.commands.complex.chassis.vision.autonomous;

import edu.greenblitz.knockdown.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.knockdown.commands.simple.chassis.DriveByGyro;
import edu.greenblitz.knockdown.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.knockdown.commands.simple.poker.*;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.knockdown.data.GearDependentDouble;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.command.WaitUntilFree;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class VisionPlaceHatchPanelForAutonomous extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0.0;

    private static final double VISION_TARGET_OFFSET = -4;

    public VisionPlaceHatchPanelForAutonomous() {
//        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new ReleaseHatch(400));
    }

    public static class Part1 extends CommandChain {
        public Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, getDynamicVisionOffset(), true));
        }
    }

    public static class DoSomething extends CommandChain {
        public DoSomething() {
            addSequential(new WaitUntilFree(Elevator.getInstance()));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 750,
                    new GearDependentDouble(0.5, 0.5), false));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 500,
                    new GearDependentDouble(0.25, 0.25)));
        }
    }

    public static class Part2 extends CommandChain {
        public Part2() {
            addParallel(new DoSomething());
            addParallel(new ExtendPoker());
        }
    }

    public static double getDynamicVisionOffset() {
        return VISION_TARGET_OFFSET;
    }
}
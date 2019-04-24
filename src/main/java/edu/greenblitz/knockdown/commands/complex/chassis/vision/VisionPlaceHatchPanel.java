package edu.greenblitz.knockdown.commands.complex.chassis.vision;

import edu.greenblitz.knockdown.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.knockdown.commands.simple.chassis.DriveByGyro;
import edu.greenblitz.knockdown.commands.simple.chassis.neutral.ToBrake;
import edu.greenblitz.knockdown.commands.simple.chassis.neutral.ToCoast;
import edu.greenblitz.knockdown.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.knockdown.commands.simple.poker.*;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.knockdown.data.GearDependentDouble;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.command.WaitUntilFree;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionPlaceHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0.0;

    private static final double VISION_TARGET_OFFSET = 1; // -1.5 before roller damage

    public VisionPlaceHatchPanel() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new ToCoast());
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new ReleaseHatch(400));
        addSequential(new Cleanup());
    }

    @Override
    protected void atInit() {
        super.atInit();
//        Chassis.getInstance().toCoast();
    }

    public static class Part1 extends CommandChain {
        public Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, getDynamicVisionOffset(), true));
//            addSequential(new DriveByGyro(ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 100,
//                    new GearDependentDouble());
        }
    }

    public static class Part2 extends CommandChain {
        public Part2() {
            addSequential(new WaitUntilFree(Elevator.getInstance()));
            addSequential(new ExtendPoker(1));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 850,
                    new GearDependentDouble(0.4, 0.4), false));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 100,
                    new GearDependentDouble(0.15, 0.15)));
        }
    }

    public static class Cleanup extends CommandChain {
        public Cleanup() {
            addSequential(new DriveByGyro(EXTEND_DISTANCE - ALIGN_DISTANCE, 600)); // was 600
            addSequential(new HoldHatch());
            addParallel(new RetractPoker());
        }
    }

    @Override
    protected void atEnd() {
        super.atEnd();
        Chassis.getInstance().toBrake();
    }

    public static double getDynamicVisionOffset() {
        return VISION_TARGET_OFFSET;
    }
}
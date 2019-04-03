package edu.greenblitz.knockdown.commands.complex.chassis.vision;

import edu.greenblitz.knockdown.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.knockdown.commands.simple.chassis.DriveByGyro;
import edu.greenblitz.knockdown.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.knockdown.commands.simple.poker.ExtendPoker;
import edu.greenblitz.knockdown.commands.simple.poker.RetractAndHold;
import edu.greenblitz.knockdown.commands.simple.poker.RetractPoker;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.knockdown.data.GearDependentDouble;
import edu.greenblitz.knockdown.subsystems.Shifter;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCollectHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0;
    private static final double VISION_TARGET_OFFSET = -1;

    private Command lastShifterCommand;
    private Shifter.Gear lastGear;

    public VisionCollectHatchPanel() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new Align());
        addSequential(new Forward());
        addSequential(new Place());
    }

    @Override
    protected void atEnd() {
        Shifter.getInstance().setDefaultCommand(lastShifterCommand);
        Shifter.getInstance().setShift(lastGear);
    }

    @Override
    protected void atInit() {
        lastGear = Shifter.getInstance().getCurrentGear();
        lastShifterCommand = Shifter.getInstance().getDefaultCommand();
    }

    private class Align extends CommandChain {
        private Align() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, getDynamicVisionOffset(),
                    true));
        }
    }

    private class Forward extends CommandChain {
        private Forward() {
            addSequential(new ExtendPoker(50)); // Needed in different commands for small delay
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 850,
                    new GearDependentDouble(0.4, 0.4), false));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 700,
                    new GearDependentDouble(0.15, 0.15)));
        }
    }

    private class Place extends CommandChain {
        private Place() {
            addSequential(new DriveByGyro(EXTEND_DISTANCE - ALIGN_DISTANCE, 600));
            addSequential(new RetractPoker());
        }
    }

    private double getDynamicVisionOffset() {
        return SmartDashboard.getNumber("VisionOffsetCollect", VISION_TARGET_OFFSET);
    }
}
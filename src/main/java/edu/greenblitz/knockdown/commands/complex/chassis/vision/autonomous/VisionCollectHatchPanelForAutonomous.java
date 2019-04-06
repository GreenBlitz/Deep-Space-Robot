package edu.greenblitz.knockdown.commands.complex.chassis.vision.autonomous;

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
import edu.wpi.first.wpilibj.command.WaitCommand;

public class VisionCollectHatchPanelForAutonomous extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0;
    private static final double VISION_TARGET_OFFSET = -1;

    private Command lastShifterCommand;
    private Shifter.Gear lastGear;

    public VisionCollectHatchPanelForAutonomous() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new Align());
        addSequential(new ExtendAndForward());
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
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, VISION_TARGET_OFFSET,
                    true));
        }
    }

    private class Forward extends CommandChain {
        private Forward() {
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 650,
                    new GearDependentDouble(0.55, 0.55), false));
            addSequential(new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE) / 2, 500,
                    new GearDependentDouble(0.25, 0.25)));
        }
    }

    private class ExtendAndForward extends CommandChain {
        private ExtendAndForward() {
            addParallel(new Forward());
            addParallel(new ExtendPoker());
        }
    }
}

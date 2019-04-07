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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCollectHatchPanelForAutonomous extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0;
    private static final double VISION_TARGET_OFFSET = -1;

    public VisionCollectHatchPanelForAutonomous() {
        addSequential(new Align());
        addParallel(new ToPower());
        addSequential(new Forward());
    }


    private class Align extends CommandChain {
        private Align() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, VISION_TARGET_OFFSET,
                    true));
        }
    }

    private class Forward extends CommandChain {
        private Forward() {
            addParallel(new ExtendPoker(), new DriveByGyro((ALIGN_DISTANCE - EXTEND_DISTANCE), 800,
                    new GearDependentDouble(0.6, 0.6), true));
        }
    }

}
package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.GearDependentDouble;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.Command;

public class VisionCollectHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.1;
    private static final double EXTEND_DISTANCE = 0.0;
    private static final double VISION_TARGET_OFFSET = 0;

    private Command lastShifterCommand;
    private Shifter.Gear lastGear;

    public VisionCollectHatchPanel() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new Cleanup());
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

    private class Part1 extends CommandChain{
        private Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, VISION_TARGET_OFFSET));
        }
    }

    private class Part2 extends CommandChain{
        private Part2() {
            addSequential(new ExtendPoker(50)); // Needed in different commands for small delay
            addSequential(new DriveStraightByDistance((ALIGN_DISTANCE - EXTEND_DISTANCE)/2, 550,
                    new GearDependentDouble(0.4, 0.4)));
            addSequential(new DriveStraightByDistance((ALIGN_DISTANCE - EXTEND_DISTANCE)/2, 800,
                    new GearDependentDouble(0.2, 0.2)));
        }
    }

    private class Cleanup extends CommandChain {
        private Cleanup() {
            addParallel(new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 300), new RetractPoker());
        }
    }
}
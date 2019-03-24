package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.Command;

public class VisionCollectHatchPanel extends CommandChain {

    private static final double ALIGN_DISTANCE = 1;
    private static final double EXTEND_DISTANCE = -0.1;
    private static final double VISION_TARGET_OFFSET = 6;

    private Command lastShifterCommand;
    private Shifter.Gear lastGear;

    public VisionCollectHatchPanel() {
        addSequential(new ToSpeed());
        addSequential(new Part1());
        addSequential(new Part2());

    }

    @Override
    protected void atEnd() {
        new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 500).start();
        new RetractPoker().start();
        Shifter.getInstance().setDefaultCommand(lastShifterCommand);
        Shifter.getInstance().setShift(lastGear);
    }


    @Override
    protected void atInit() {
        lastGear = Shifter.getInstance().getCurrentGear();
        lastShifterCommand = Shifter.getInstance().getDefaultCommand();
    }

    @Override
    protected void atInterrupt() {}

    private class Part1 extends CommandChain{
        private Part1() {
            addParallel(new RetractAndHold(), new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, VISION_TARGET_OFFSET));
        }
    }

    private class Part2 extends CommandChain{
        private Part2() {
            addSequential(new ExtendPoker(50)); // Needed in different commands for small delay
            addSequential(new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 600));
        }
    }
}
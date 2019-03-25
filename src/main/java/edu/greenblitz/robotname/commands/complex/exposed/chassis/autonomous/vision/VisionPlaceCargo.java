package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.complex.exposed.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;

public class VisionPlaceCargo extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.9;
    private static final double EXTEND_DISTANCE = 0.2;

    public VisionPlaceCargo() {
        addSequential(new ToSpeed());
        addSequential(new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        addSequential(new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 600));
    }

    @Override
    protected void atEnd() {
        new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 500).start();
        new SafeMoveElevator(Elevator.Level.GROUND).start();
    }

    @Override
    protected void atInterrupt() {}
}
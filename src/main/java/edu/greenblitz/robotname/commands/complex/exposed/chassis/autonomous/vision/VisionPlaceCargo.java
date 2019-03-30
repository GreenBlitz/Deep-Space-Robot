package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.complex.exposed.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.exposed.kicker.KickBall;
import edu.greenblitz.robotname.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;

public class VisionPlaceCargo extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0.5;

    public VisionPlaceCargo() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        addSequential(new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 600));
        addSequential(new KickBall());
        addSequential(new DriveStraightByDistance(EXTEND_DISTANCE - ALIGN_DISTANCE, 600));
    }

    @Override
    public void atInit(){
        if (Elevator.getInstance().isFloorLevel()){
            this.cancel();
        }
    }

    @Override
    protected void atEnd() {
        new SafeMoveElevator(Elevator.Level.GROUND).start();
    }

    @Override
    protected void atInterrupt() {}
}
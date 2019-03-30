package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.complex.exposed.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.exposed.kicker.KickBall;
import edu.greenblitz.robotname.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.GearDependentDouble;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;

public class VisionPlaceCargo extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0.0;
    private static final double VISION_TARGET_OFFSET = 0;

    public VisionPlaceCargo() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, VISION_TARGET_OFFSET, true));
        addSequential(new DriveStraightByDistance(0.5*(ALIGN_DISTANCE - EXTEND_DISTANCE), 500));
        addSequential(new DriveStraightByDistance(0.5*(ALIGN_DISTANCE - EXTEND_DISTANCE), 500,
                new GearDependentDouble(0.2, 0.2)));
        addSequential(new KickBall());
    }

    @Override
    public void atInit(){
        if (Elevator.getInstance().isFloorLevel()){
            this.cancel();
        }
    }
}
package edu.greenblitz.robotname.commands.complex.chassis.vision;

import edu.greenblitz.robotname.commands.complex.kicker.KickBall;
import edu.greenblitz.robotname.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.robotname.commands.simple.chassis.DriveByGyro;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
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
        addSequential(new DriveByGyro(0.5*(ALIGN_DISTANCE - EXTEND_DISTANCE), 850, false));
        addSequential(new DriveByGyro(0.5*(ALIGN_DISTANCE - EXTEND_DISTANCE), 800,
                new GearDependentDouble(0.25, 0.25)));
    }

    @Override
    public void atInit(){
        if (Elevator.getInstance().isFloorLevel() && Elevator.getInstance().getCurrentCommandName().equals
                (Elevator.getInstance().getDefaultCommandName())){
            this.cancel();
        }
    }

    @Override
    public void atInterrupt(){ }

    @Override
    public void atEnd(){
        new KickBall().start();
    }
}
package edu.greenblitz.knockdown.commands.complex.chassis.vision;

import edu.greenblitz.knockdown.commands.complex.kicker.KickAndRetract;
import edu.greenblitz.knockdown.commands.complex.kicker.KickBall;
import edu.greenblitz.knockdown.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.knockdown.commands.simple.chassis.DriveByGyro;
import edu.greenblitz.knockdown.commands.simple.chassis.neutral.ToCoast;
import edu.greenblitz.knockdown.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.knockdown.data.GearDependentDouble;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionPlaceCargo extends CommandChain {

    private static final double ALIGN_DISTANCE = 1.2;
    private static final double EXTEND_DISTANCE = 0.0;
    private static final double VISION_TARGET_OFFSET = 1; // -1 before roller damage

    public VisionPlaceCargo() {
        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new ToCoast());
        addSequential(new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE, getDynamicVisionOffset(), true));
        addSequential(new DriveByGyro(0.5*(ALIGN_DISTANCE - EXTEND_DISTANCE), 850, false));
        addSequential(new DriveByGyro(0.5*(ALIGN_DISTANCE - EXTEND_DISTANCE), 650,
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
        new KickAndRetract().start();
        Chassis.getInstance().toBrake();
    }

    public static double getDynamicVisionOffset() {
        return VISION_TARGET_OFFSET;
    }
}
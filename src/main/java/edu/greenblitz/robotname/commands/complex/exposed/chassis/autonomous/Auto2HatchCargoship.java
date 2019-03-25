package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.exposed.HoldHatchAndMoveToFloor;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.ChangeTargetFocus;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.DriveByTime;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.*;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

public class Auto2HatchCargoship extends CommandChain {
    private static final long POKER_TIMEOUT = 100;

    public Auto2HatchCargoship() {
        addParallel(new ChangeTargetFocus(VisionMaster.Focus.MIDDLE), new ToSpeed());

        addSequential(new APPCCommand(
                new Path<>(APPCCommand.getPath("Cargoship1.pf1.csv")),
                new Position(-0.651 - 2.5, 1.6, Math.PI),
                0.6, 0.35, true, 0.3, .6, .4, .2/*0.6*/));

        addSequential(new VisionPlaceHatchPanel());

        addSequential(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship2.pf1.csv")),
                        null, 0.6, 0.15, true,
                        0.2, 0.4, 0.4, 0.6));
        addSequential(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship3.pf1.csv")), null, .8, 0.2, false, 0.1,
                        .3, 0.4, .2)
        );
        addSequential(new VisionCollectHatchPanel());
        addSequential(new ChangeTargetFocus(VisionMaster.Focus.MIDDLE));
        addSequential(new APPCCommand(
                new Path<>(APPCCommand.getPath("Cargoship4.pf1.csv")),
                null, .6, .2, true,
                .1, 0.5, 0.4, .2
        ));

        addSequential(new VisionPlaceHatchPanel());
    }
}
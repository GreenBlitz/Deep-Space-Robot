package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.*;
import edu.greenblitz.utils.command.chain.CommandChain;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

//import edu.greenblitz.robotname.commands.simple.chassis.vision.SetLocalizerLocationByVisionTarget;

public class Auto2HatchCargoship extends CommandChain {

    @Override
    protected void initChain() {
        addSequential(new APPCCommand(
                new Path<>(APPCCommand.getPath("Cargoship1.pf1.csv")),
                new Position(-0.651 - 2.5, 1.6, Math.PI),
                0.6, 0.1, true, 0.2, 1, .4, .2/*0.6*/));

        addSequential(new VisionPlaceHatchPanel());

        addSequential(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship2.pf1.csv")),
                        null, 0.6, 0.1, true,
                        0.1, 0.4, 0.4, 0.6));
        addSequential(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship3.pf1.csv")), null, .8, 0.15, false, 0.1,
                        .3, 0.4, .2)
        );

        addSequential(new VisionCollectHatchPanel());

        addSequential(new APPCCommand(
                new Path<>(APPCCommand.getPath("Cargoship4.pf1.csv")),
                null, .6, .2, true,
                .1, 0.5, 0.4, .2
        ));

        addSequential(new VisionPlaceHatchPanel());
    }
}
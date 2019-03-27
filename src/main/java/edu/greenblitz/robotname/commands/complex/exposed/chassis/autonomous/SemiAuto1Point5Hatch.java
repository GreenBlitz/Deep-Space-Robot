package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.exposed.HoldHatchAndMoveToFloor;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.ChangeTargetFocus;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.ArcadeUntilVision;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizerWithGyro;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;
import org.opencv.core.Mat;

/**
 * Like two crago hatch but starts at cargohip
 */
public class SemiAuto1Point5Hatch extends CommandChain {

    public SemiAuto1Point5Hatch() {

        addParallel(new ResetLocalizer(0, 0, Math.PI), new ChangeTargetFocus(VisionMaster.Focus.RIGHT), new ToSpeed());

        addSequential(new ArcadeUntilVision());
        addSequential(new ToPower());
        addSequential(new VisionPlaceHatchPanel.Part1());
        addSequential(new VisionPlaceHatchPanel.Part2());
        addSequential(new ResetLocalizerWithGyro(-3.36, 6.6));
        // TODO This ^ assumes the robot is inside the cargoship, do some mesurments to fix
        addSequential(new VisionPlaceHatchPanel.Cleanup());

        addSequential(new ToSpeed());

        addSequential(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship2.pf1.csv")),
                        null, 0.6, 0.15, true,
                        0.2, 0.4, 0.5, 0.6));
        addSequential(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship3.pf1.csv")), null, 1.2, 1.7, false, 0.1,
                        .3, 0.7, .4)
        );
        addSequential(new VisionCollectHatchPanel());
        addParallel(new ToSpeed(), new ChangeTargetFocus(VisionMaster.Focus.MIDDLE));
        addSequential(new APPCCommand(
                new Path<>(APPCCommand.getPath("Cargoship4.pf1.csv")),
                null, 1, 1, true,
                .1, 1.5, 0.6, .4
        ));

        addSequential(new VisionPlaceHatchPanel());

        //new Position(-3.36, 6.6, -Math.PI/2)
    }

}
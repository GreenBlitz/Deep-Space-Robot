package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.exposed.HoldHatchAndMoveToFloor;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.ChangeTargetFocus;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;

/**
 * Like two crago hatch but starts at cargohip
 */
public class SemiAuto1Point5Hatch extends CommandChain {

    public SemiAuto1Point5Hatch() {

//        addSequential(new ChangeTargetFocus(VisionMaster.Focus.RIGHT));
//
//        addSequential(new ToSpeed());
//        addSequential(new VisionPlaceHatchPanel.Part1());
//        addSequential(new VisionPlaceHatchPanel.Part2());
//        addSequential(new ReleaseHatch());
//        addSequential(new ResetLocalizer(new Position(-3.36, 6.6, -Math.PI/2)));
//
//        addSequential(new ToSpeed());
//
//        addSequential(new APPCCommand(Paths.get("Cargoship2"),
//                0.6, 0.2, true, 0.25, 0.4, 0.5, 0.4)
//        );
//
//        addSequential(new HoldHatchAndMoveToFloor());
//
//        addSequential(new APPCCommand(Paths.get("Cargoship3"),
//                .8, 0.25, false, 0.3, .3, 0.3, .6)
//        );
//
//        addSequential(new VisionCollectHatchPanel());
//
//        addSequential(new ToSpeed());
//
//        addParallel(new ChangeTargetFocus(VisionMaster.Focus.MIDDLE), new APPCCommand(Paths.get("Cargoship4"),
//                .8, .25, true, .2, 1, 0.6, .6)
//        );
//
//        addSequential(new VisionPlaceHatchPanel());
    }

}
package edu.greenblitz.robotname.commands.complex.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.robotname.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.chassis.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.FallWithNavx;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;

public class AutoThreeHalfFarRocket extends CommandChain {

    public AutoThreeHalfFarRocket(boolean left) {

        addSequential(new FallWithNavx());

        addParallel(new ChangeTargetFocus(VisionMaster.Focus.RIGHT),
                new ResetLocalizer(new Position(-3, 1.7, 0)));

        addSequential(new ToSpeed());

        addSequential(new APPCCommand(
                Paths.get("RocketFastHalf1", left),
                null,
                0.8, 0.4, false, 0.35,
                .6, .6, .2));

        addSequential(new APPCCommand(
                Paths.get("RocketSlowHalf1", left),
                null,
                0.8, 0.2, false, 0.3,
                1, .4, .1, 0.8));

        addSequential(new VisionPlaceHatchPanel());

        addSequential(new ToSpeed());

        addSequential(new APPCCommand(Paths.get("Rocket2", left),
                null, 0.6, 0.2, true,
                0.3, 0.4, 0.5, 0.4, 155, 7));
        addParallel(new RetractPoker());

        addSequential(
                new APPCCommand(Paths.get("Rocket3", left), null, 2,
                        0.4, false,
                        0.4, 2.7, 0.9, .2, 1.7)
        );

        addSequential(new VisionCollectHatchPanel());

    }
}

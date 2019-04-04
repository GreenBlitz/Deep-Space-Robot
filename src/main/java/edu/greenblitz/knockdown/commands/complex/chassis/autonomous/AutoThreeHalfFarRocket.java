package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionPlaceHatchPanel;
import edu.greenblitz.knockdown.commands.simple.chassis.FallWithNavx;
import edu.greenblitz.knockdown.commands.simple.chassis.ResetNavx;
import edu.greenblitz.knockdown.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.knockdown.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.knockdown.commands.simple.poker.RetractPoker;
import edu.greenblitz.knockdown.commands.simple.shifter.ToSpeed;
import edu.greenblitz.knockdown.data.Paths;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;

public class AutoThreeHalfFarRocket extends CommandChain {

    public AutoThreeHalfFarRocket(boolean left, boolean fall) {

        if (fall)
            addSequential(new FallWithNavx());

        if (left) {
            addParallel(new ChangeTargetFocus(VisionMaster.Focus.MIDDLE),
                    new ResetLocalizer(new Position(-3, 1.7, 0)));
        } else {
            addParallel(new ChangeTargetFocus(VisionMaster.Focus.MIDDLE),
                    new ResetLocalizer(new Position(-8.2 + 3, 1.7, 0)));
        }

        addSequential(new ToSpeed());

        addSequential(new APPCCommand(
                Paths.get("L_RocketFastHalf1", left),
                null,
                0.8, 0.4, false, 0.35,
                .6, .6, .8));

        addSequential(new APPCCommand(
                Paths.get("L_RocketSlowHalf1", left),
                null,
                0.8, 0.2, false, 0.3,
                1, .3, .1, 1.4));

        addSequential(new VisionPlaceHatchPanel());

        addSequential(new ToSpeed());

        addSequential(new APPCCommand(Paths.get("L_Rocket2", left),
                null, 0.6, 0.2, true,
                0.2, 0.4, 0.5, 0.4, 155, 7));
        addParallel(new RetractPoker());

        addSequential(
                new APPCCommand(Paths.get("L_Rocket3", left), null, 2,
                        0.4, false,
                        0.4, 2.7, 0.6, .3, 1.7)
        );

        addSequential(new VisionCollectHatchPanel());

    }

}

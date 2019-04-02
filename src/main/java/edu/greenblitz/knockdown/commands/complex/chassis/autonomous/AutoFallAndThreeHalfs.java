package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionPlaceHatchPanel;
import edu.greenblitz.knockdown.commands.simple.chassis.FallWithNavx;
import edu.greenblitz.knockdown.commands.simple.chassis.ResetNavx;
import edu.greenblitz.knockdown.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.knockdown.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.knockdown.commands.simple.poker.*;
import edu.greenblitz.knockdown.commands.simple.shifter.ToSpeed;
import edu.greenblitz.knockdown.data.Paths;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;

public class AutoFallAndThreeHalfs extends CommandChain {
    private long tStart;

    public AutoFallAndThreeHalfs(boolean left, boolean fall) {

        addSequential(new ResetNavx());

        if (fall)
            addSequential(new FallWithNavx());

        if (left)
            addParallel(new ChangeTargetFocus(VisionMaster.Focus.RIGHT),
                new ResetLocalizer(new Position(-3, 1.7, 0)));
        else
            addParallel(new ChangeTargetFocus(VisionMaster.Focus.LEFT),
                    new ResetLocalizer(new Position(-8.2 + 3, 1.7, 0)));

        addSequential(new ToSpeed());

        addSequential(new APPCCommand(
                Paths.get("L_FallAndPlace", left),
                null,
                0.8, 0.2, false, 0.3,
                1, .45, .1, 1.5));

        addSequential(new VisionPlaceHatchPanel());

        addSequential(new ToSpeed());
        addParallel(new RetractPoker());

        addSequential(new APPCCommand(Paths.get("L_Cargoship2", left),
                null, 0.6, 0.6, true,
                0, 0.75, 0.5, 0.4));
        addSequential(
                new APPCCommand(Paths.get("L_Cargoship3", left), null, 2,
                        0.4, false,
                        0.4, 2.7, 1, .1, 2)
        );
        addSequential(new VisionCollectHatchPanel());
    }

    public AutoFallAndThreeHalfs(boolean left){
        this(left, true);
    }

    @Override
    protected void atInit() {
        tStart = System.currentTimeMillis();
    }

    @Override
    protected void atEnd() {
        long now = System.currentTimeMillis();
        logger.debug("FINISHED 2 CARGO AUTO, TAKING {} MS = {} S", now - tStart, (now - tStart) / 1000.0);
    }
}
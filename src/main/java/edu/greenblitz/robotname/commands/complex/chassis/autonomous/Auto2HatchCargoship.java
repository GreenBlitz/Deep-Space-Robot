package edu.greenblitz.robotname.commands.complex.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.robotname.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.chassis.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;

public class Auto2HatchCargoship extends CommandChain {
    private static final long POKER_TIMEOUT = 100;
    private long tStart;

    public Auto2HatchCargoship() {
        addParallel(new ChangeTargetFocus(VisionMaster.Focus.RIGHT), new ToSpeed(),
                new ResetLocalizer(new Position(-0.651 - 2.5, 1.6, Math.PI)));

        addSequential(new APPCCommand(
                Paths.getRaw("Cargoship1"),
                null,
                0.6, 0.5, true, 0.4, 2, .7, .4/*0.6*/));

        addSequential(new VisionPlaceHatchPanel());

        addSequential(new ToSpeed());

        addSequential(
                new APPCCommand(Paths.getRaw("Cargoship2"),
                        null, 0.6, 0.15, true,
                        0.2, 0.4, 0.5, 0.6));
        addSequential(
                new APPCCommand(Paths.getRaw("Cargoship3"), null, 1.2, 1.7, false, 0.1,
                        .3, 0.7, .4)
        );

        addSequential(new VisionCollectHatchPanel());
        addParallel(new ToSpeed(), new ChangeTargetFocus(VisionMaster.Focus.MIDDLE));

        addSequential(new APPCCommand(
                Paths.getRaw("Cargoship4"),
                null, 1, 1, true,
                .1, 1.5, 0.6, .4
        ));

        addSequential(new VisionPlaceHatchPanel());
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
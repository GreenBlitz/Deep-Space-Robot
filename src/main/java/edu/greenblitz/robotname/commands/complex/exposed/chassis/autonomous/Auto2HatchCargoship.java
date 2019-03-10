package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.DropFromHeight;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.utils.command.chain.CommandChain;

public class Auto2HatchCargoship extends CommandChain {
    private static final long POKER_TIMEOUT = 100;

    @Override
    protected void initChain() {
        addParallel(new ResetLocalizer(-3.151, 1.6, Math.PI));
        addSequential(new DropFromHeight());

        addParallel(new APPCCommand(Paths.get("Cargoship1"),
                        0.6, 0.2,
                        true, 0.1, 0.7, .6, .4)
        );

        addSequential(new VisionPlaceHatchPanel());

        addParallel(new APPCCommand(Paths.get("Cargoship2"),
                        0.6, 0.1, true, 0.1, 0.4, 0.4, 0.6)
        );

        addSequential(new APPCCommand(Paths.get("Cargoship3"),
                .8, 0.15, false, 0.1, .3, 0.4, .2)
        );

        addSequential(new VisionCollectHatchPanel());

        addSequential(new APPCCommand(Paths.get("Cargoship4"),
                .8, .2, true, .1, 0.5, 0.4, .2)
        );

        addSequential(new VisionPlaceHatchPanel());
    }
}
package edu.greenblitz.robotname.commands.complex.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.robotname.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.chassis.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.FallWithNavx;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.commands.simple.poker.*;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

public class AutoFallAndThreeHalfs extends CommandChain {
    private long tStart;

    public AutoFallAndThreeHalfs() {
        addSequential(new FallWithNavx());

        addParallel(new ChangeTargetFocus(VisionMaster.Focus.RIGHT),
                new ResetLocalizer(new Position(-3, 1.7, 0)));

        addSequential(new ToSpeed());

        addSequential(new APPCCommand(
                new Path<>(APPCCommand.getPath("FallAndPlace.pf1.csv")),
                null,
                0.8, 0.2, false, 0.3,
                1, .45, .1, 1.5));

        addSequential(new VisionPlaceHatchPanel());

        addSequential(new ToSpeed());
        addParallel(new RetractPoker());

        addSequential(new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship2.pf1.csv")),
                        null, 0.6, 0.5, true,
                        0, 0.7, 0.5, 0.4));
        addSequential(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship3.pf1.csv")), null, 2,
                        0.4, false,
                        0.4, 2.7, 1, .1, 1.5)
        );
        addSequential(new VisionCollectHatchPanel());
//        addParallel(new ToSpeed(), new ChangeTargetFocus(VisionMaster.Focus.MIDDLE));
//        addSequential(new APPCCommand(
//                new Path<>(APPCCommand.getPath("Cargoship4.pf1.csv")),
//                null, 1, 1, true,
//                .1, 1.5, 0.4, .4
//        ));
//
//        addSequential(new VisionPlaceHatchPanel());
    }

    @Override
    protected void atInit(){
        tStart = System.currentTimeMillis();
    }

    @Override
    protected void atEnd(){
        long now = System.currentTimeMillis();
        logger.debug("FINISHED 2 CARGO AUTO, TAKING {} MS = {} S", now - tStart, (now - tStart)/1000.0);
    }
}
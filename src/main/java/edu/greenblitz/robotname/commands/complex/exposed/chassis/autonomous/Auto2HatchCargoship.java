package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.DropFromHeight;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.commands.simple.chassis.neutral.ToCoast;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.utils.command.chain.CommandChain;

public class Auto2HatchCargoship extends CommandChain {

    private static final double PLACE_ALIGN_DISTANCE = 0.7;
    private static final double PLACE_EXTEND_DISTANCE = 0.3;

    private static final double COLLECT_ALIGN_DISTANCE = 0.7;
    private static final double COLLECT_EXTEND_DISTANCE = 0.15;

    private static final long POKER_TIMEOUT = 100;

    @Override
    protected void initChain() {
        addParallel(new ResetLocalizer(-3.151, 1.6, Math.PI));
        addSequential(new DropFromHeight());

        addSequential(new APPCCommand(Paths.get("Cargoship1"),
                0.6, 0.2, true, 0.2, 0.3, .7, .2)
        );

        addParallel(new RetractAndHold(POKER_TIMEOUT), new DriveToDistanceFromVisionTarget(PLACE_ALIGN_DISTANCE));
        addParallel(new ExtendPoker(POKER_TIMEOUT), new DriveStraightByDistance(PLACE_ALIGN_DISTANCE - PLACE_EXTEND_DISTANCE, 1000));
        addParallel(new ReleaseHatch(POKER_TIMEOUT), new DriveStraightByDistance(PLACE_EXTEND_DISTANCE - PLACE_ALIGN_DISTANCE, 1000));

        addParallel(new RetractAndHold(POKER_TIMEOUT),
                new APPCCommand(Paths.get("Cargoship2"),
                        0.6, 0.1, true, 0.1, 0.4, 0.4, 0.6)
        );

        addSequential(new APPCCommand(Paths.get("Cargoship3"),
                .8, 0.15, false, 0.1, .3, 0.4, .2)
        );

        addParallel(new DriveToDistanceFromVisionTarget(COLLECT_ALIGN_DISTANCE));
        addParallel(new ExtendPoker(POKER_TIMEOUT), new DriveStraightByDistance(COLLECT_ALIGN_DISTANCE - COLLECT_EXTEND_DISTANCE, 1000));
        addParallel(new RetractPoker(POKER_TIMEOUT), new DriveStraightByDistance(COLLECT_EXTEND_DISTANCE - COLLECT_ALIGN_DISTANCE, 1000));

        addSequential(new APPCCommand(Paths.get("Cargoship4"),
                .6, .2, true, .1, 0.5, 0.4, .2
        ));

        addSequential(new VisionPlaceHatchPanel());
    }
}
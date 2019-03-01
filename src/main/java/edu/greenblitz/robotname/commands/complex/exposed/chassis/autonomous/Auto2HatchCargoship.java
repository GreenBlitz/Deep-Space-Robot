package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.poker.CompletePoker;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.MultiPathAPPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToVisionTarget;
import edu.greenblitz.robotname.commands.simple.chassis.vision.SetLocalizerLocationByVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.Paths;
import edu.greenblitz.utils.VisionTargetLocations;
import edu.greenblitz.utils.command.chain.CommandChain;

public class Auto2HatchCargoship extends CommandChain {

    @Override
    protected void initChain() {
        //TODO: Calibrate APPC parameters.
        var state = OI.State.HATCH;
        addSequential(new APPCCommand(Paths.get("Vis Cargoship1"), 0.5, 0.2, false, 0.3, 0.5, 1));
        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.CARGO_SHIP.heightByState(state)));
        addSequential(new CompletePoker());
        addSequential(new SetLocalizerLocationByVisionTarget(VisionTargetLocations.Cargoship.Left.SECTION1));
        addParallel(
                new SafeMoveElevator(Elevator.Level.GROUND.heightByState(state)),
                new MultiPathAPPCCommand("MotionDrive(Pure Cargoship2, Vis Cargoship3)",
                                                new APPCCommand(Paths.get("Pure Cargoship2"), 0.5, 0.2, false, 0.3, 0.5, 1),
                                                new APPCCommand(Paths.get("Vis Cargoship3"), 0.5, 0.2, false, 0.3, 0.5, 1)));
        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.GROUND.heightByState(state)), new ExtendPoker());
        addSequential(new SetLocalizerLocationByVisionTarget(VisionTargetLocations.Feeder.LEFT));
        addParallel(new RetractPoker(),
                new SafeMoveElevator(Elevator.Level.GROUND.heightByState(state)),
                new MultiPathAPPCCommand("MotionDrive(Pure Cargoship4, Vis Cargoship5)",
                                                new APPCCommand(Paths.get("Pure Cargoship4"), 0.5, 0.2, false, 0.3, 0.5, 1),
                                                new APPCCommand(Paths.get("Vis Cargoship5"), 0.5, 0.2, false, 0.3, 0.5, 1)));
        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.CARGO_SHIP.heightByState(state)));
        addSequential(new CompletePoker());
        addSequential(new SetLocalizerLocationByVisionTarget(VisionTargetLocations.Cargoship.Left.SECTION2));
    }
}
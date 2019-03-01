package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.poker.CompletePoker;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.Paths;
import edu.greenblitz.utils.command.chain.CommandChain;

public class Auto2HatchCargoship extends CommandChain {

    @Override
    protected void initChain() {
        //TODO: Calibrate APPC parameters.
        addSequential(new APPCCommand(Paths.get("Vis Cargoship1"), 0.5, 0.2, false, 0.3, 0.5, 1));
        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.Hatch.SHIP));
        addSequential(new CompletePoker());
        addParallel(
                new SafeMoveElevator(Elevator.Level.Hatch.CRUISE),
                new CommandChain("MotionDrive(Pure Cargoship2, Vis Cargoship3)") {
                    @Override
                    protected void initChain() {
                        addSequential(new APPCCommand(Paths.get("Pure Cargoship2"), 0.5, 0.2, false, 0.3, 0.5, 1));
                        addSequential(new APPCCommand(Paths.get("Vis Cargoship3"), 0.5, 0.2, false, 0.3, 0.5, 1));
                    }
                });
        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.Hatch.COLLECT), new ExtendPoker());
        addParallel(new RetractPoker(),
                new SafeMoveElevator(Elevator.Level.Hatch.CRUISE),
                new CommandChain("MotionDrive(Pure Cargoship4, Vis Cargoship5)") {
                    @Override
                    protected void initChain() {
                        addSequential(new APPCCommand(Paths.get("Pure Cargoship4"), 0.5, 0.2, false, 0.3, 0.5, 1));
                        addSequential(new APPCCommand(Paths.get("Vis Cargoship5"), 0.5, 0.2, false, 0.3, 0.5, 1));
                    }
                });
        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.Hatch.SHIP));
        addSequential(new CompletePoker());
    }
}
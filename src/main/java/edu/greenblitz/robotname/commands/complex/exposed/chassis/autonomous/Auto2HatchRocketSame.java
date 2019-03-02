//package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;
//
//import edu.greenblitz.robotname.OI;
//import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
//import edu.greenblitz.robotname.commands.complex.hidden.poker.FullPokerCycle;
//import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
//import edu.greenblitz.robotname.commands.simple.chassis.motion.MultiPathAPPCCommand;
//import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToVisionTarget;
//import edu.greenblitz.robotname.commands.simple.chassis.vision.SetLocalizerLocationByVisionTarget;
//import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
//import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
//import edu.greenblitz.robotname.subsystems.Elevator;
//import edu.greenblitz.utils.Paths;
//import edu.greenblitz.utils.VisionTargetLocations;
//import edu.greenblitz.utils.command.chain.CommandChain;
//
//public class Auto2HatchRocketSame extends CommandChain {
//
//    @Override
//    protected void initChain() {
//        //TODO: Calibrate APPC parameters.
//        var state = OI.State.HATCH;
//        addSequential(new APPCCommand(Paths.get("Vis Rocket1"), 0.5, 0.2, false, 0.3, 0.5, 1));
//        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.ROCKET_HIGH.heightByState(state)));
//        addSequential(new FullPokerCycle());
//        addSequential(new SetLocalizerLocationByVisionTarget(VisionTargetLocations.Rocket.Left.LEFT));
//        addParallel(
//                new SafeMoveElevator(Elevator.Level.GROUND.heightByState(state)),
//                new MultiPathAPPCCommand("MotionDrive(Pure Rocket2, Vis Rocket3)",
//                                                new APPCCommand(Paths.get("Pure Rocket2"), 0.5, 0.2, false, 0.3, 0.5, 1),
//                                                new APPCCommand(Paths.get("Vis Rocket3"), 0.5, 0.2, false, 0.3, 0.5, 1)));
//        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.GROUND.heightByState(state)), new ExtendPoker());
//        addSequential(new SetLocalizerLocationByVisionTarget(VisionTargetLocations.Feeder.LEFT));
//        addParallel(new RetractPoker(),
//                new SafeMoveElevator(Elevator.Level.GROUND.heightByState(state)),
//                new MultiPathAPPCCommand("MotionDrive(Pure Rocket Same-Side4, Vis Rocket Same-Side5)",
//                                                new APPCCommand(Paths.get("Pure Rocket Same-Side4"), 0.5, 0.2, false, 0.3, 0.5, 1),
//                                                new APPCCommand(Paths.get("Vis Rocket Same-Side5"), 0.5, 0.2, false, 0.3, 0.5, 1)));
//        addParallel(new DriveToVisionTarget(), new SafeMoveElevator(Elevator.Level.ROCKET_MID.heightByState(state)));
//        addSequential(new FullPokerCycle());
//        addSequential(new SetLocalizerLocationByVisionTarget(VisionTargetLocations.Rocket.Left.LEFT));
//    }
//}
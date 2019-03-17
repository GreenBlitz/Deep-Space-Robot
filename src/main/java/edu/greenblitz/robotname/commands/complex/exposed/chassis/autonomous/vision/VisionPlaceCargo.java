package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.commands.complex.exposed.kicker.KickBall;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.subsystems.*;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionPlaceCargo extends CommandChain {

    private static final double ALIGN_DISTANCE = 0.9;
    private static final double EXTEND_DISTANCE = 0.2;

    public VisionPlaceCargo() {
        addSequential(new Part1());
        addSequential(new Part2());
        addSequential(new Part3());
        clearRequirements();
        requires(Chassis.getInstance());
        requires(Poker.getInstance());
        requires(Kicker.getInstance());
        SmartDashboard.putBoolean("Req Elevator", doesRequire(Elevator.getInstance()));
        SmartDashboard.putBoolean("Req Chassis", doesRequire(Chassis.getInstance()));
        SmartDashboard.putBoolean("Req Poker", doesRequire(Poker.getInstance()));
        SmartDashboard.putBoolean("Req Kicker", doesRequire(Kicker.getInstance()));
        SmartDashboard.putBoolean("Req Roller", doesRequire(Roller.getInstance()));
        SmartDashboard.putBoolean("Req Big", doesRequire(Climber.getInstance().getBig()));
        SmartDashboard.putBoolean("Req Extender", doesRequire(Climber.getInstance().getExtender()));
        SmartDashboard.putBoolean("Req Wheels", doesRequire(Climber.getInstance().getWheels()));
    }

    @Override
    protected void atEnd() {
//        new SafeMoveElevator(Elevator.Level.GROUND).start();
    }

    @Override
    protected void atInterrupt() {}

    private class Part1 extends CommandChain {
        private Part1() {
            addParallel(new DriveToDistanceFromVisionTarget(ALIGN_DISTANCE));
        }
    }

    private class Part2 extends CommandChain{
        private Part2() {
            addParallel(new DriveStraightByDistance(ALIGN_DISTANCE - EXTEND_DISTANCE, 1000));
        }
    }

    private class Part3 extends CommandChain {
        private Part3() {
            addParallel(new KickBall());
        }
    }
}
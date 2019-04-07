package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.autonomous.VisionCollectHatchPanelForAutonomous;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.autonomous.VisionPlaceHatchPanelForAutonomous;
import edu.greenblitz.knockdown.commands.complex.elevator.SafeMoveElevator;
import edu.greenblitz.knockdown.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.knockdown.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.knockdown.commands.simple.poker.RetractAndHold;
import edu.greenblitz.knockdown.commands.simple.shifter.ToSpeed;
import edu.greenblitz.knockdown.data.Paths;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;
import org.greenblitz.motion.base.Position;

public class Auto2FarRocket extends CommandChain {

    private long tStart;

    @Override
    protected void atInit() {
        super.atInit();
        tStart = System.currentTimeMillis();
    }

    public Auto2FarRocket(boolean left) {
        addSequential(new DriveToRocket1AndMoveElevator(left));

        addSequential(new VisionPlaceHatchPanelForAutonomous());

        addSequential(new DriveBack(left));

        addSequential(new MoveElevatorDownDriveToFeeder(left));

        addSequential(new VisionCollectHatchPanelForAutonomous());

        addSequential(new DriveToRocket2(left));

        addSequential(new VisionPlaceHatchPanelForAutonomous());

        clearRequirements();
        requires(Chassis.getInstance());
    }

    private class DriveToRocket1AndMoveElevator extends CommandChain {
        private DriveToRocket1AndMoveElevator(boolean left) {
            if (left)
                addParallel(new ResetLocalizer(new Position(- 3, 1.4, Math.PI)), new ChangeTargetFocus(VisionMaster.Focus.RIGHT));
            else
                addParallel(new ResetLocalizer(new Position(-8.2 + 3, 1.4, Math.PI)), new ChangeTargetFocus(VisionMaster.Focus.LEFT));
            addParallel(new ToSpeed(), new OI.ToHatchMode());

            addParallel(
                    new APPCCommand(Paths.get("2FarRocket1", left), null, 2,
                            0.35, true,
                            0.2, 1.5, 0.8, .1

                    ));
            addParallel(new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        }
    }

    private class DriveBack extends CommandChain {
        private DriveBack(boolean left) {
            addParallel(new ToSpeed(),
                    new APPCCommand(Paths.get("2FarRocket2", left), null, 2,
                            0.2, true,
                            0.3, 1, 0.5, .1));
        }
    }

    private class MoveElevatorDownDriveToFeeder extends CommandChain {
        private MoveElevatorDownDriveToFeeder(boolean left) {
            addParallel(
                    new APPCCommand(Paths.get("2FarRocket3", left), null, 2,
                            .5, false,
                            0.4, 2, 0.8, .1, 3));
            addParallel(new SafeMoveElevator(Elevator.Level.GROUND));
            addParallel(new RetractAndHold());
        }
    }

    private class DriveToRocket2 extends CommandChain {
        private DriveToRocket2(boolean left) {
            addParallel(new ToSpeed());
            addParallel(
                    new APPCCommand(Paths.get("2FarRocket4", left), null, 2,
                            0.35, true,
                            0.3, 2, 0.8, .1));
//            addParallel(new SafeMoveElevator(Elevator.Level.ROCKET_MID));
            addParallel(new RetractAndHold());
        }
    }

    @Override
    protected void atEnd() {
        super.atEnd();
        long dt = System.currentTimeMillis() - tStart;
        logger.info("APPC FINISHED TAKING {} ms = {} s", dt, dt / 1000.0);
    }
}
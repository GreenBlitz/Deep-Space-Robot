package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.HoldAllCommands;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystickRestricted;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.roller.ToggleRoller;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.SetLocalizerLocation;
import edu.greenblitz.robotname.commands.simple.chassis.vision.AlignToVisionTarget;
import edu.greenblitz.robotname.commands.simple.chassis.vision.ArcPidDriveToVisionTarget;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToDistanceFromVisionTarget;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.robotname.commands.simple.shifter.ToggleShift;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.Paths;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.ResetCommands;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.buttons.POVButton;
import org.greenblitz.motion.base.Position;

import java.util.Optional;

public class OI {
    public enum GameObject {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static SmartJoystick sideJoystick;
    private static GameObject oiGameObject = GameObject.HATCH;

    public static SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public static SmartJoystick getSideJoystick() {
        return sideJoystick;
    }

    private static class ToHatchMode extends GBCommand {
        @Override
        public Optional<State> getDeltaState() {
            return Optional.empty();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void initialize() {
            oiGameObject = GameObject.HATCH;
        }
    }

    private static class ToCargoMode extends GBCommand {
        @Override
        public Optional<State> getDeltaState() {
            return Optional.empty();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void initialize() {
            oiGameObject = GameObject.CARGO;
        }
    }

    public static void initJoysticks() {
        if (mainJoystick == null) mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
//        if (sideJoystick == null) sideJoystick = new SmartJoystick(RobotMap.Joysticks.SIDE);
    }

    public static void initBindings() {
//        initUnsafeBindings(); // For real game shit
        initUntestedBindings(); // For testing code
    }

    private static void initUntestedBindings() {
        mainJoystick.R1.whenPressed(new DriveToVisionTarget());
        mainJoystick.R1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));

        mainJoystick.START.whenReleased(new HoldAllCommands());

        mainJoystick.A.whenPressed(new VisionCollectHatchPanel());
        mainJoystick.A.whenReleased(new ArcadeDriveByJoystick(mainJoystick));

        mainJoystick.L3.whenPressed(new ToggleShift());
        mainJoystick.Y.whenPressed(new TogglePokerExtender());

        mainJoystick.X.whenPressed(new DriveToDistanceFromVisionTarget(0.9));
        mainJoystick.X.whenReleased(new ArcadeDriveByJoystick(mainJoystick));
        mainJoystick.B.whenPressed(new DriveStraightByDistance(0.7));
        mainJoystick.B.whenReleased(new ArcadeDriveByJoystick(mainJoystick));

        mainJoystick.L1.whenPressed(new CommandChain() {
            @Override
            protected void initChain() {
                addSequential(new SetLocalizerLocation(new Position(3.5034, 1.7092)));
                addSequential(new APPCCommand(Paths.get("Cargoship1"), 0.5, 0.2, false, 0.1, 0.5, 0.3));
            }
        });
    }

    private static void initUnsafeBindings() {
        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());
        mainJoystick.A.whenPressed(new TogglePokerExtender());
        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.B.whenReleased(new HoldHatch());
        mainJoystick.L1.whenPressed(new ExtendAndRollIn());
        mainJoystick.L1.whenReleased(new RetractAndStopRoller());
        mainJoystick.L3.whenPressed(new ToggleShift());
        mainJoystick.R1.whenPressed(new AlignToVisionTarget());
        mainJoystick.X.whenPressed(new KickBall());
        mainJoystick.Y.whenPressed(new ToggleRoller());

        sideJoystick.L3.whenPressed(new ResetCommands());
        sideJoystick.R1.whenPressed(new SafeMoveElevator(Elevator.Level.GROUND));
        sideJoystick.A.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_LOW));
        sideJoystick.B.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        sideJoystick.Y.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_HIGH));
        sideJoystick.X.whenPressed(new SafeMoveElevator(Elevator.Level.CARGO_SHIP));
        sideJoystick.L1.whenReleased(new ToggleRoller());

        var pov = new POVButton(sideJoystick.getRawJoystick(), 0);
        pov.whenPressed(new ClimbByJoystickRestricted(mainJoystick, mainJoystick, sideJoystick));

        sideJoystick.START.whenPressed(new ToCargoMode());
        sideJoystick.BACK.whenPressed(new ToHatchMode());
    }

    public static GameObject getOIState() {
        return oiGameObject;
    }

    public static boolean isStateCargo() {
        return getOIState() == GameObject.CARGO;
    }

    public static boolean isStateHatch() {
        return getOIState() == GameObject.HATCH;
    }

    public static void update() {
    }
}
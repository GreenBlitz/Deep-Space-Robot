package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.HoldAllCommands;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystickRestricted;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.roller.ToggleRoller;
import edu.greenblitz.robotname.commands.simple.chassis.DriveStraightByDistance;
import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCChain;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.SetLocalizerLocation;
import edu.greenblitz.robotname.commands.simple.chassis.vision.*;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerHolder;
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
import org.greenblitz.motion.pathing.Path;

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
//        mainJoystick.R1.whenPressed(new DriveToVisionTarget());
//        mainJoystick.R1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));
//
//        mainJoystick.START.whenReleased(new HoldAllCommands());
//
//        mainJoystick.A.whenPressed(new VisionCollectHatchPanel());
////        mainJoystick.A.whenReleased(new ArcadeDriveByJoystick(mainJoystick));
//
        mainJoystick.L1.whenPressed(new VisionPlaceHatchPanel());
        mainJoystick.L1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));
//
//        mainJoystick.X.whenPressed(new TogglePokerHolder());
//        mainJoystick.Y.whenPressed(new TogglePokerExtender());


        mainJoystick.A.whenPressed(new APPCCommand(
                new Path<>(APPCCommand.getPath("Cargoship1.pf1.csv")),
                new Position(-3.08, 1.55, Math.PI),
                0.6, 0.2, true, 0.2, 0.7, .4, .2/*0.6*/));

        mainJoystick.X.whenPressed(new APPCChain(
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship2.pf1.csv")),
                        new Position(-3.37, 6.6, -Math.PI/2), 0.6, 0.1, true,
                        0.1, 0.4, 0.4, 0.6),
                new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship3.pf1.csv")), null, .5, 0.15, false, 0.1,
                        .3, 0.4, .2)
        ));

        mainJoystick.B.whenPressed(new APPCCommand(new Path<>(APPCCommand.getPath("Cargoship3.pf1.csv")),
                new Position(-1.984, 7.11, Math.PI), .5, 0.2, false, 0.1,
                0.5, 0.4, .2));


        // TODO note start location isn't correct, it's moved so we will have space on bama
        mainJoystick.Y.whenPressed(new APPCCommand(
                new Path<>(APPCCommand.getPath("Cargoship4.pf1.csv")),
                new Position(-0.68, 1.202, -Math.PI), 1, .2, true,
                .1, 1.5, 0.5, .6
        ));
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
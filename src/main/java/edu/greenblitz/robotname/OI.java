package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystickRestricted;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.MoveElevatorByLevel;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.poker.ButtonReleaseAndHold;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeExtendAndRollIn;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeRetractAndStop;
import edu.greenblitz.robotname.commands.complex.hidden.roller.ToggleRoller;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToVisionTarget;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.ButtonKickAndRetract;
import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStop;
import edu.greenblitz.robotname.commands.simple.shifter.ToggleShift;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.ResetCommands;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.buttons.POVButton;

import java.util.Optional;

public class OI {
    public enum GameObject {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static SmartJoystick sideJoystick;
    private static GameObject oiGameObject;

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
        if (sideJoystick == null) sideJoystick = new SmartJoystick(RobotMap.Joysticks.SIDE);
    }

    public static void initBindings() {
        oiGameObject = GameObject.HATCH;
        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());

        // UNSAFE COMMANDS!!!
        mainJoystick.A.whenPressed(new TogglePokerExtender());
        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.B.whenReleased(new HoldHatch());

//        initUnsafeBindings();

        // testing
        initUntestedBindings();

    }

    private static void initUntestedBindings() {
        mainJoystick.X.whenPressed(new KickBall());
        mainJoystick.Y.whenPressed(new ToggleRoller());
    }

    private static void initUnsafeBindings() {
        mainJoystick.L1.whenPressed(new ExtendAndRollIn());
        mainJoystick.L1.whenReleased(new RetractAndStop());
        mainJoystick.L3.whenPressed(new ToggleShift());
        mainJoystick.R1.whenPressed(new DriveToVisionTarget());
        mainJoystick.X.whenPressed(new Kick());
        mainJoystick.X.whenReleased(new Unkick());
        mainJoystick.Y.whenPressed(new ToggleRoller());

        sideJoystick.L3.whenPressed(new ResetCommands());
        sideJoystick.R1.whenPressed(new MoveElevatorByLevel(Elevator.Level.GROUND));
        sideJoystick.A.whenPressed(new MoveElevatorByLevel(Elevator.Level.ROCKET_LOW));
        sideJoystick.B.whenPressed(new MoveElevatorByLevel(Elevator.Level.ROCKET_MID));
        sideJoystick.Y.whenPressed(new MoveElevatorByLevel(Elevator.Level.ROCKET_HIGH));
        sideJoystick.X.whenPressed(new MoveElevatorByLevel(Elevator.Level.CARGO_SHIP));
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
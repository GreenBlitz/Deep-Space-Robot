package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeExtendAndRollOut;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeRetractAndStop;
import edu.greenblitz.robotname.commands.simple.chassis.vision.DriveToVisionTarget;
import edu.greenblitz.robotname.commands.simple.poker.ExtendAndRelease;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.ResetCommands;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;

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
        mainJoystick.B.whenPressed(new ExtendAndRelease());
        mainJoystick.B.whenReleased(new RetractAndHold());


        // testing
//        initUntestedBindings();
    }

    private static void initUntestedBindings() {
        mainJoystick.L1.whenPressed(new SafeExtendAndRollOut());
        mainJoystick.L1.whenReleased(new SafeRetractAndStop());
        mainJoystick.L3.whenPressed(new ResetCommands());
        mainJoystick.R1.whenPressed(new DriveToVisionTarget());
        mainJoystick.X.whenPressed(new KickBall());

        sideJoystick.L3.whenPressed(new ResetCommands());
        sideJoystick.R1.whenPressed(new SafeMoveElevator(Elevator.Level.GROUND));
        sideJoystick.A.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_LOW));
        sideJoystick.B.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        sideJoystick.Y.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_HIGH));
        sideJoystick.X.whenPressed(new SafeMoveElevator(Elevator.Level.CARGO_SHIP));

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
//        oiState = sideJoystick.STATE_SWITCH.get() ? State.CARGO : State.HATCH;
    }
}
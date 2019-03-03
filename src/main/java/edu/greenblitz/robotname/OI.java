package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.roller.*;
import edu.greenblitz.robotname.commands.simple.poker.*;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.hid.SmartJoystick;

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

    public static void initJoysticks() {
        if (mainJoystick == null) mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        if (sideJoystick == null) sideJoystick = new SmartJoystick(RobotMap.Joysticks.SIDE);
    }

    public static void initBindings() {

        oiGameObject = GameObject.CARGO;

        mainJoystick.L1.whenPressed(new SafeExtendAndRollIn());
        mainJoystick.L1.whenReleased(new SafeRetractAndStop());
        mainJoystick.X.whenPressed(new KickBall());
        mainJoystick.A.whenPressed(new TogglePokerExtender());
        mainJoystick.B.whenPressed(new ExtendAndRelease());
        mainJoystick.B.whenReleased(new RetractAndHold());

        mainJoystick.Y.whenPressed(new SafeMoveElevator(Elevator.Level.CARGO_SHIP));
        mainJoystick.R1.whenPressed(new ExtendRoller());

        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());

//        mainJoystick.X.whenPressed(new SafeRetractAndRollIn());
//        mainJoystick.Y.whenPressed(new ToggleKicker());
//        mainJoystick.B.whenPressed(new TogglePokerExtender());
//        mainJoystick.R1.whenPressed(new StopRolling());

//        mainJoystick.A.whenPressed(new KickBall());
//        mainJoystick.B.whenPressed(new TogglePokerExtender());
//        mainJoystick.X.whenPressed(new ToggleRoller());
//
//        sideJoystick.SMALL_RED.whenPressed(new PrepareToExchangeGameObject(State.CARGO, Elevator.Level.GROUND));
//        sideJoystick.BOTTOM_SMALL_GREEN.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.CARGO_SHIP));
//        sideJoystick.SMALL_YELLOW.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.ROCKET_LOW));
//        sideJoystick.SMALL_BLUE.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.ROCKET_MID));
//        sideJoystick.TOP_SMALL_GREEN.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.ROCKET_HIGH));
//
//        //TODO: decide on good buttons
//        sideJoystick.LEFT_WHITE.whenActive(new ClimbByJoystick());
//        sideJoystick.RIGHT_WHITE.whenPressed(new SafeExtendAndRollIn());
//        sideJoystick.RIGHT_WHITE.whenReleased(new SafeRetractAndRollIn());
//        sideJoystick.RIGHT_BLACK.whenPressed(new EnsurePokerExtended());
//        sideJoystick.RIGHT_BLACK.whenReleased(new EnsurePokerRetracted());
//        sideJoystick.LEFT_BLACK.whenPressed(new HoldHatch());
//        sideJoystick.LEFT_BLACK.whenReleased(new ReleaseHatch());
//        sideJoystick.BIG_YELLOW.whenPressed(new KickerEnsureBallOut());
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
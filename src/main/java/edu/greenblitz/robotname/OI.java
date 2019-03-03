package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeExtendAndRollIn;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeRetractAndStop;
import edu.greenblitz.robotname.commands.simple.poker.ExtendAndRelease;
import edu.greenblitz.robotname.commands.simple.poker.RetractAndHold;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
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

//        mainJoystick.START.whenPressed(new SafeMoveElevator(Elevator.Level.GROUND));
//        mainJoystick.BACK.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_MID));

        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());
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
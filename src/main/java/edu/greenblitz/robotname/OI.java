package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.simple.kicker.ToggleKicker;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.ToggleRoller;
import edu.greenblitz.utils.hid.CustomControlBoard;
import edu.greenblitz.utils.hid.SmartJoystick;

public class OI {
    public enum State {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static CustomControlBoard sideJoystick;
    private static State oiState;

    public static SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public static CustomControlBoard getSideJoystick() {
        return sideJoystick;
    }

    public static void init() {
        mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        sideJoystick = new CustomControlBoard(RobotMap.Joysticks.SIDE);

        mainJoystick.A.whenPressed(new ToggleRoller());
        mainJoystick.B.whenPressed(new TogglePokerExtender());
    }

    public static State getOIState() {
        return oiState;
    }

    public static boolean isStateCargo() {
        return getOIState() == State.CARGO;
    }

    public static boolean isStateHatch() {
        return getOIState() == State.HATCH;
    }

    public static void update() {

    }
}

package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.SafeKick;
import edu.greenblitz.robotname.commands.complex.hidden.roller.ToggleRoller;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollOut;
import edu.greenblitz.robotname.commands.simple.roller.RollIn;
import edu.greenblitz.robotname.commands.simple.roller.RollOut;
import edu.greenblitz.robotname.commands.simple.shifter.ToggleShift;
import edu.greenblitz.utils.hid.CustomControlBoard;
import edu.greenblitz.utils.hid.SmartJoystick;

import javax.management.MBeanAttributeInfo;

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

    public static void initJoysticks() {
        if (mainJoystick == null) mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        if (sideJoystick == null) sideJoystick = new CustomControlBoard(RobotMap.Joysticks.SIDE);
    }

    public static void initBindings() {
        mainJoystick.A.whenPressed(new SafeKick());
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

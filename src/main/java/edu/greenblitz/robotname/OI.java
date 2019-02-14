package edu.greenblitz.robotname;

import edu.greenblitz.utils.SmartJoystick;

public class OI {
    private static SmartJoystick mainJoystick, sideJoystick;

    public static SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public static SmartJoystick getSideJoystick() {
        return sideJoystick;
    }

    public static void init() {
        mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        sideJoystick = new SmartJoystick(RobotMap.Joysticks.SIDE);
    }

    public static void update() {

    }
}
package edu.greenblitz.robotname;

import edu.greenblitz.robotname.utils.SmartJoystick;

public class OI {

    private static OI instance;

    private SmartJoystick mainJoystick, sideJoystick;

    private OI() {
        mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        sideJoystick = new SmartJoystick(RobotMap.Joysticks.SIDE);
    }

    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSideJoystick() {
        return sideJoystick;
    }

    public static void init() {
        if (instance == null)
            instance = new OI();
    }

    public static OI getInstance() {
        init();
        return instance;
    }

    public void update() {

    }
}
package edu.greenblitz.knockdown;


import edu.greenblitz.knockdown.commands.RollerCollectBall;

public class OI {

    SmartJoystick stick;

    private static OI instance;

    public static OI getInstance() {
        if (instance == null) {instance = new OI();}
        return instance;
    }

    private OI() {
        this.stick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        setupControls();
    }

    private void setupControls(){

        stick.L1.whileHeld(new RollerCollectBall());

    }
}
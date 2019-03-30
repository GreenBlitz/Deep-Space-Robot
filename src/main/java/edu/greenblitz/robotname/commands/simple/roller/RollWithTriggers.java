package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.RollerState;
import edu.wpi.first.wpilibj.Joystick;

public class RollWithTriggers extends RollerBaseCommand{

    protected SmartJoystick stick;

    public RollWithTriggers(SmartJoystick stick){
        this.stick = stick;
    }

//    @Override
//    protected void atInit(){
//        system.setDefaultCommand(this);
//    }

    @Override
    protected void execute(){
        double value = stick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER)
                - stick.getAxisValue(SmartJoystick.Axis.LEFT_TRIGGER);
        if (value > 0.05)
            system.rollOut();
        else if (value < -0.05)
            system.rollIn();
        else
            system.stopRolling();
    }

    @Override
    protected boolean isFinished(){
        return false;
    }

    @Override
    protected RollerState getNextState() {
        return null;
    }
}

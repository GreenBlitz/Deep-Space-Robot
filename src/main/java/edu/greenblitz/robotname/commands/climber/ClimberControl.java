/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.climber;

import edu.greenblitz.utils.command.JoystickCommand;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.SmartJoystick;

public class ClimberControl extends JoystickCommand<Climber> {

    public ClimberControl(SmartJoystick joystick) {
        super(Climber.getInstance(), joystick);
    }

    public ClimberControl() { this(OI.getMainJoystick()); }

    @Override
    protected void execute() {
        system.setExtender(
                SmartJoystick.Axis.RIGHT_TRIGGER.getValue(joystick) -
                SmartJoystick.Axis.LEFT_TRIGGER.getValue(joystick));
        system.setWheels(
                SmartJoystick.Axis.LEFT_Y.getValue(joystick));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

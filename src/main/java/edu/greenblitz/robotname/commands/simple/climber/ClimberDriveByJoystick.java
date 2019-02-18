/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.utils.command.JoystickCommand;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.SmartJoystick;

public class ClimberDriveByJoystick extends JoystickCommand<Climber> {

    public ClimberDriveByJoystick(SmartJoystick joystick) {
        super(Climber.getInstance(), joystick);
    }

    public ClimberDriveByJoystick() { this(OI.getMainJoystick()); }

    @Override
    protected void execute() {
        system.drive(SmartJoystick.Axis.LEFT_Y.getValue(joystick));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
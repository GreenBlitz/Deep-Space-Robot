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
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ClimberDriveByJoystick extends JoystickCommand<Climber.Wheels> {

    public ClimberDriveByJoystick(SmartJoystick joystick) {
        super(Climber.getInstance().getWheels(), joystick);
    }

    public ClimberDriveByJoystick() { this(OI.getMainJoystick()); }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
        system.drive(SmartJoystick.Axis.RIGHT_TRIGGER.getValue(joystick) - SmartJoystick.Axis.LEFT_TRIGGER.getValue(joystick));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
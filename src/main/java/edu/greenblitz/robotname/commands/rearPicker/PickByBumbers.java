/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.rearPicker;

import edu.greenblitz.utils.SmartJoystick;
import edu.greenblitz.utils.command.JoystickCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.RearPicker;

public class PickByBumbers extends JoystickCommand<RearPicker> {

    public PickByBumbers(SmartJoystick joystick) {
        super(RearPicker.getInstance(), joystick);
    }

    public PickByBumbers() { this(OI.getMainJoystick()); }

    @Override
    protected void execute() {
        if (OI.getMainJoystick().L1.get() && OI.getMainJoystick().R1.get())
            system.setState(Value.kOff);
        else if (OI.getMainJoystick().R1.get())
            system.setState(Value.kForward);
        else if (OI.getMainJoystick().L1.get())
            system.setState(Value.kReverse);
        else
            system.setState(Value.kOff);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.picker;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.RearPicker;
import edu.wpi.first.wpilibj.command.Command;

public class MotorPickByBumpers extends Command {
  public MotorPickByBumpers() {
    requires(RearPicker.getInstance());
  }

  @Override
  protected void execute() {
    if (OI.getMainJoystick().L1.get() && OI.getMainJoystick().R1.get())
      RearPicker.getInstance().setPower(0);
    else if (OI.getMainJoystick().R1.get() && !RearPicker.getInstance().isRaised())
      RearPicker.getInstance().setPower(-0.6);
    else if (OI.getMainJoystick().L1.get() && !RearPicker.getInstance().isLowered())
      RearPicker.getInstance().setPower(0.6);
    else
      RearPicker.getInstance().setPower(0);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

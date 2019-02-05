/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.rearPicker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.RearPicker;

public class PickByBumbers extends Command {

  public PickByBumbers() {
    requires(RearPicker.getInstance());
  }

  @Override
  protected void execute() {
    if (OI.getInstance().getMainJoystick().L1.get() && OI.getInstance().getMainJoystick().R1.get())
      RearPicker.getInstance().setState(Value.kOff);
    else if (OI.getInstance().getMainJoystick().R1.get())
      RearPicker.getInstance().setState(Value.kForward);
    else if (OI.getInstance().getMainJoystick().L1.get())
      RearPicker.getInstance().setState(Value.kReverse);
    else
      RearPicker.getInstance().setState(Value.kOff);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}

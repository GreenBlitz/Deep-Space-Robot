/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.rearPicker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.RearPicker;

public class LowerRearPicker extends Command {
  
  public LowerRearPicker() {
    requires(RearPicker.getInstance());
  }

  @Override
  protected void execute() {
    RearPicker.getInstance().setState(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}

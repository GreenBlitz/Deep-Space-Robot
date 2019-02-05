/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.kicker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Kicker;

public class ReturnKicker extends Command {
  
  public ReturnKicker() {
    requires(Kicker.getInstance());
  }

  @Override
  protected void execute() {
    Kicker.getInstance().setState(Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}

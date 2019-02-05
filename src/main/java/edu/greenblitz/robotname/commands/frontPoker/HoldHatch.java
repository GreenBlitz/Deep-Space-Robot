/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class HoldHatch extends Command {
 
  public HoldHatch() {
    requires(FrontPoker.getInstance());
  }

  @Override
  protected void execute() {
    FrontPoker.getInstance().setKicker(Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
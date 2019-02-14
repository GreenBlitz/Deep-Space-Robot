/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ReturnKicker extends SubsystemCommand<Kicker> {
  
  public ReturnKicker() {
    super(Kicker.getInstance());
  }

  @Override
  protected void execute() {
    system.setState(Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.kicker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Kicker;

public class KickCargo extends SubsystemCommand<Kicker> {
  
  public KickCargo() {
    super(Kicker.getInstance());
  }

  @Override
  protected void execute() {
    system.setState(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}

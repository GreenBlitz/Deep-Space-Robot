/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class BrakeElevator extends Command {
  public BrakeElevator() {
    requires(Elevator.getInstance());
  }

  @Override
  protected void execute() {
    Elevator.getInstance().setState(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

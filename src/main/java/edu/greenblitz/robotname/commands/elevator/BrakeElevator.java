/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class BrakeElevator extends SubsystemCommand<Elevator> {

  private static final double POWER = 0.05;

  public BrakeElevator(Elevator elevator) {
    super(elevator);
  }

  @Override
  protected void initialize() {
    system.setState(Value.kForward);
  }

  @Override
  protected void execute() {
    system.setPower(POWER);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

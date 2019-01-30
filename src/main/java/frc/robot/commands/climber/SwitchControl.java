/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Elevator;

public class SwitchControl extends Command {

  public SwitchControl() {
    requires(Elevator.getInstance());
    requires(Chassis.getInstance());
  }

  @Override
  protected void execute() {
    Chassis.getInstance().setDefaultCommand(null);
    Elevator.getInstance().setDefaultCommand(new OpenClimber());
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}

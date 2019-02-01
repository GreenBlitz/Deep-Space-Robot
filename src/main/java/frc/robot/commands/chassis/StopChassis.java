/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Chassis;

public class StopChassis extends Command {
  
  public StopChassis() {
    requires(Chassis.getInstance());
  }

  @Override
  protected void execute() {
    Chassis.getInstance().tankDrive(0, 0);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}

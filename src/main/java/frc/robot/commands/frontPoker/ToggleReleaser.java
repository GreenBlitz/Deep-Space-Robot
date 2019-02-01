/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.FrontPoker;

public class ToggleReleaser extends Command {
  
  public ToggleReleaser() {
    requires(FrontPoker.getInstance());
  }

  @Override
  protected void execute() {
    FrontPoker.getInstance().setKicker(FrontPoker.getInstance().getKickerState() == Value.kForward ?
                                      Value.kReverse : Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.wpi.first.wpilibj.command.Command;

public class RollCargoOut extends Command {
  public RollCargoOut() {
    requires(Roller.getInstance());
  }

  @Override
  protected void execute() {
    Roller.getInstance().setPower(-1);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

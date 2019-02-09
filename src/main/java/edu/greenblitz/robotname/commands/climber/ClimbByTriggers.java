/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.SmartJoystick;

public class ClimbByTriggers extends Command {
  public ClimbByTriggers() {
    requires(Climber.getInstance());
  }

  @Override
  protected void execute() {
    Climber.getInstance().setExtender(SmartJoystick.Axis.RIGHT_TRIGGER.getValue(OI.getInstance().getMainJoystick()) - 
                                      SmartJoystick.Axis.LEFT_TRIGGER.getValue(OI.getInstance().getMainJoystick()));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}

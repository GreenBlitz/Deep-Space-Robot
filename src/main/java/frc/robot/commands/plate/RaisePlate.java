/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.plate;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Plate;

public class RaisePlate extends Command {

  public RaisePlate() {
    requires(Plate.getInstance());
  }

  @Override
  protected void execute() {
    Plate.getInstance().setState(Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}

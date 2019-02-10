/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class LowerElevator extends Command implements PIDSource, PIDOutput {
  private static final double kP = 0.5, kI = 0, kD = 0;

  private static int m_timesOnTarget = 0;
  private PIDController m_controller;

  public LowerElevator(double height) {
    requires(Elevator.getInstance());
    m_controller = new PIDController(kP, kI, kD, this, this);
    m_controller.setAbsoluteTolerance(0.05);
    m_controller.setSetpoint(height);
    m_controller.setOutputRange(-0.8, 0);
  }

  @Override
  protected void initialize() {
    m_controller.enable();
  }

  @Override
  protected void execute() {
    if(m_controller.onTarget())
      m_timesOnTarget++;
    else
      m_timesOnTarget = 0;
  }

  @Override
  protected boolean isFinished() {
    return m_timesOnTarget >= 4;
  }

  @Override
  protected void end() {
    m_controller.disable();
    Elevator.getInstance().setPower(0);
    Elevator.getInstance().setState(Value.kForward);
  }

  @Override
  public void pidWrite(double output) {
    Elevator.getInstance().setPower(output);
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {}

  @Override
  public PIDSourceType getPIDSourceType() {
    return PIDSourceType.kDisplacement;
  }

  @Override
  public double pidGet() {
    return Elevator.getInstance().getHeight();
  }
}

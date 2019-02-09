package edu.greenblitz.robotname.commands.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.wpi.first.wpilibj.command.Command;

public class ActivateCompressorBelow extends Command {

  private static final double DEADZONE = 5;

  private double m_pressure;

  public ActivateCompressorBelow(double pressure) {
    requires(Pneumatics.getInstance());
    m_pressure = pressure;
  }

  @Override
  protected void execute() {
    if (Pneumatics.getInstance().getPressure() < m_pressure && !Pneumatics.getInstance().isActive())
      Pneumatics.getInstance().setCompressor(true);
    else if (Pneumatics.getInstance().getPressure() > m_pressure + DEADZONE && Pneumatics.getInstance().isActive())
      Pneumatics.getInstance().setCompressor(false);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
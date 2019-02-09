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
    if (Pneumatics.getInstance().isLimitOn()) {
      if (Pneumatics.getInstance().getPressure() < m_pressure && !Pneumatics.getInstance().isEnabled())
        Pneumatics.getInstance().setCompressor(true);
      else if (Pneumatics.getInstance().getPressure() > m_pressure + DEADZONE && Pneumatics.getInstance().isEnabled())
        Pneumatics.getInstance().setCompressor(false);
    }
    else if (!Pneumatics.getInstance().isEnabled())
      Pneumatics.getInstance().setCompressor(true);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
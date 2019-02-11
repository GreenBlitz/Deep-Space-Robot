package edu.greenblitz.robotname.commands.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.wpi.first.wpilibj.command.Command;

public class ActivateCompressorBelow extends Command {

  private static final double DEFAULT_DEADZONE = 10;

  private double m_limit;
  private double m_deadzone;

  public ActivateCompressorBelow(double pressure, double deadzone) {
    requires(Pneumatics.getInstance());
    m_limit = pressure;
    m_deadzone = deadzone;
  }

  public ActivateCompressorBelow(double pressure) {
    this(pressure, DEFAULT_DEADZONE);
  }

  @Override
  protected void execute() {
    if (Pneumatics.getInstance().isLimitOn()) {
      if (Pneumatics.getInstance().getPressure() < m_limit)
        Pneumatics.getInstance().setCompressor(true);
      else if (Pneumatics.getInstance().getPressure() > m_limit + m_deadzone)
        Pneumatics.getInstance().setCompressor(false);
    }
    else
      Pneumatics.getInstance().setCompressor(true);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
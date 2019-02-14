package edu.greenblitz.robotname.commands.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.command.Command;

public class ActivateCompressorBelow extends SubsystemCommand<Pneumatics> {

  private static final double DEFAULT_DEADZONE = 10;

  private double m_limit;
  private double m_deadzone;

  public ActivateCompressorBelow(double pressure, double deadzone) {
    super(Pneumatics.getInstance());
    m_limit = pressure;
    m_deadzone = deadzone;
  }

  public ActivateCompressorBelow(double pressure) {
    this(pressure, DEFAULT_DEADZONE);
  }

  @Override
  protected void execute() {
    if (system.isLimitOn()) {
      if (system.getPressure() < m_limit)
        system.setCompressor(true);
      else if (system.getPressure() > m_limit + m_deadzone)
        system.setCompressor(false);
    }
    else
      system.setCompressor(true);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
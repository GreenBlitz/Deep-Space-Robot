package edu.greenblitz.robotname.commands.roller;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Roller;

public class CloseAndCollectCargo extends Command {
  private static final long timeout = 1000;

  CloseAndCollectCargo() {
    super(timeout/1000.0);
    requires(Roller.getInstance());
  }

  @Override
  protected void initialize() {
    Roller.getInstance().setExtender(Value.kReverse);
  }

  @Override
  protected void execute() {
    Roller.getInstance().setPower(0.4);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Roller.getInstance().setPower(0);
  }
}
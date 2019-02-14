package edu.greenblitz.robotname.commands.roller;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Roller;

public class CloseAndCollectCargoByLimitSwitch extends Command {
  public CloseAndCollectCargoByLimitSwitch() {
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
    return Roller.getInstance().isBallFullyIn();
  }

  @Override
  protected void end() {
    Roller.getInstance().setPower(0);
  }
}
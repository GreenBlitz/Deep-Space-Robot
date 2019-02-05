package edu.greenblitz.robotname.utils.command.background;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class RunBackgroundCommands extends Command {
  RunBackgroundCommands() {
    requires(BackgroundSubsystem.getInstance());
  }

  @Override
  protected void initialize() {
    for (Command command : BackgroundSubsystem.getCommands()) {
      Scheduler.getInstance().add(command);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
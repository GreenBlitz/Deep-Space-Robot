package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public class AddCommandToElevatorQueue extends Command {

  private Command m_command;
  
  public AddCommandToElevatorQueue(Command command) {
    m_command = command;
  }

  @Override
  protected void execute() {
    Elevator.getInstance().commandQueue.addCommand(m_command);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
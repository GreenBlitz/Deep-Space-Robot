package frc.utils.command.background;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import java.util.ArrayList;

public class BackgroundSubsystem extends Subsystem {

  private static final BackgroundSubsystem instance = new BackgroundSubsystem();
  private static ArrayList<Command> m_commands = new ArrayList<>();

  public static void addCommand(Command command) {
    m_commands.add(command);
  }

  static ArrayList<Command> getCommands() {
    return m_commands;
  }

  private BackgroundSubsystem() {}

  static BackgroundSubsystem getInstance() {
    return instance;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new RunBackgroundCommands());
  }
}
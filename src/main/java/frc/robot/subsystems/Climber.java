package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.Climber.*;

public class Climber extends Subsystem {

  private static Climber instance;

  private Climber() {
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Climber();
  }

  public static Climber getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    
  }
}
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.Spike.*;

public class Spike extends Subsystem {

  private static Spike instance;

  private Spike() {
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Spike();
  }

  public static Spike getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    
  }
}
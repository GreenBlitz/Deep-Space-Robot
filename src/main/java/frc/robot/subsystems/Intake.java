package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.Intake.*;

public class Intake extends Subsystem {

  private static Intake instance;

  private Intake() {
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Intake();
  }

  public static Intake getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    
  }
}
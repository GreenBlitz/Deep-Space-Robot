package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.Plate.*;

public class Plate extends Subsystem {

  private static Plate instance;

  private Plate() {
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Plate();
  }

  public static Plate getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    
  }
}
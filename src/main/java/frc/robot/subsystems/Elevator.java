package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.Elevator.*;

public class Elevator extends Subsystem {

  private static Elevator instance;

  private Elevator() {
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Elevator();
  }

  public static Elevator getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    
  }
}
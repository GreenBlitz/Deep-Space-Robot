package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.Kicker.*;

public class Kicker extends Subsystem {

  private static Kicker instance;

  private Kicker() {
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Kicker();
  }

  public static Kicker getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    
  }
}
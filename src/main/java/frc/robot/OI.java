package frc.robot;

import frc.utils.SmartJoystick;

public class OI {

  private static OI instance;

  private SmartJoystick mainJoystick, sideJoystick;

  private OI() {
    mainJoystick = new SmartJoystick(RobotMap.Joysticks.Main);
    sideJoystick = new SmartJoystick(RobotMap.Joysticks.Side);
  }

  public SmartJoystick getMainJoystick() {
    return mainJoystick;
  }

  public SmartJoystick getSideJoystick() {
    return sideJoystick;
  }

  public static void init() {
    if (instance == null) 
      instance = new OI();
  }

  public static OI getInstance() {
    if (instance == null) 
      init();
    return instance;
  }

  public void update() {

  }  
}
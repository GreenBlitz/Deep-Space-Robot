package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.utils.SmartJoystick;

public class OI {

  private static OI instance;

  private SmartJoystick mainJoystick, sideJoystick;

  private NetworkTable visionTable;

  private OI() {
    visionTable = NetworkTableInstance.getDefault().getTable("vision");

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

  public double getVisionRPM() {
		return visionTable.getEntry("RPM").getDouble(1000);
	}

	public double getVisionDistance() {
	    return visionTable.getEntry("Distance").getDouble(0);
	}
	
	public double getHatchAngle() {
		return visionTable.getEntry("hatch::angle").getDouble(0);
	}

	public double getHatchDistance() {
	  return visionTable.getEntry("hatch::distance").getDouble(0);
  }

  public NetworkTable getVisionTable() {
	  return  visionTable;
  }

  public void update() {

  }  
}
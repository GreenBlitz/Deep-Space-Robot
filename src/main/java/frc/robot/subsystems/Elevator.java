package frc.robot.subsystems;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.utils.Tuple;
import frc.utils.enums.ElevatorLevel;

public class Elevator extends Subsystem {

  private static Elevator instance;

  private ElevatorLevel m_level; //TODO: Add sendable chooser

  private static final double SAFE_TO_LOWER_DOWN = 0.05, 
                              SAFE_TO_LOWER_UP = 0.4,
                              SAFETY_RANGE = 0.05;

  private static final ArrayList<Tuple<Double, Double>> DANGER_ZONES = new ArrayList<Tuple<Double, Double>>();

  private Elevator() {
    DANGER_ZONES.add(new Tuple<Double, Double>(SAFE_TO_LOWER_DOWN-SAFETY_RANGE, SAFE_TO_LOWER_UP+SAFETY_RANGE));
  }

  public boolean isInDangerZone() {
    for (Tuple<Double, Double> dangerZone : DANGER_ZONES) {
      if (getHeight() > dangerZone.first() && getHeight() < dangerZone.second())
        return true;
    }
    return false;
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

  public void setLevel(ElevatorLevel level) {
    m_level = level;
  }

  public ElevatorLevel getLevel() {
    return m_level;
  }

  public double getHeight() {
    return 0;
  }

  public void update() {
    SmartDashboard.putString("Elevator::Command", getCurrentCommandName());
  }
}
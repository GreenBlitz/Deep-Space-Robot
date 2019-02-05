package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.robotname.RobotMap.Elevator.ElevatorLevel;
import edu.greenblitz.robotname.RobotMap.Elevator.Motor;
import edu.greenblitz.robotname.RobotMap.Elevator.Sensor;
import edu.greenblitz.robotname.RobotMap.Elevator.Solenoid;
import edu.greenblitz.robotname.utils.Tuple;
import edu.greenblitz.robotname.utils.encoder.IEncoder;
import edu.greenblitz.robotname.utils.encoder.Taloncoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public class Elevator extends Subsystem {

  private static final double LEVEL_HEIGHT_RANGE = 0;
  private static final double SAFE_TO_LOWER_DOWN = 0.05, 
                              SAFE_TO_LOWER_UP = 0.4,
                              SAFETY_RANGE = 0.05;
  private static final ArrayList<Tuple<Double, Double>> DANGER_ZONES = new ArrayList<Tuple<Double, Double>>();
  
  private static Elevator instance;

  private ElevatorLevel m_level; //TODO: Add sendable chooser
  
  private WPI_TalonSRX m_motor;
  private IEncoder m_encoder;
  private DoubleSolenoid m_piston;

  private Elevator() {
    DANGER_ZONES.add(new Tuple<>(SAFE_TO_LOWER_DOWN - SAFETY_RANGE, SAFE_TO_LOWER_UP + SAFETY_RANGE));
    m_motor = new WPI_TalonSRX(Motor.ELEVATOR);
    m_encoder = new Taloncoder(Sensor.TICKS_PER_METER, m_motor);
    m_piston = new DoubleSolenoid(Solenoid.FORWARD, Solenoid.REVERSE);
    m_encoder.reset();
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
    return m_encoder.getNormalizedTicks();
  }

  public void setState(Value value) {
    m_piston.set(value);
  }

  public void update() {
    SmartDashboard.putString("ELEVATOR::Command", getCurrentCommandName());

    for (ElevatorLevel level : ElevatorLevel.values()) {
      if (Math.abs(Elevator.getInstance().getHeight() - level.getHeight()) <= LEVEL_HEIGHT_RANGE) {
        Elevator.getInstance().setLevel(level);
        break;
      }
    }
  }
}
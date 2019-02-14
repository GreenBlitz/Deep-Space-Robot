package edu.greenblitz.robotname.subsystems;

import java.util.ArrayList;

import edu.greenblitz.robotname.RobotMap.Elevator.ElevatorLevel;
import edu.greenblitz.robotname.RobotMap.Elevator.Motor;
import edu.greenblitz.robotname.RobotMap.Elevator.Sensor;
import edu.greenblitz.robotname.RobotMap.Elevator.Solenoid;
import edu.greenblitz.robotname.commands.elevator.BrakeElevator;
import edu.greenblitz.utils.Tuple;
import edu.greenblitz.utils.ctre.SmartTalon;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.Taloncoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

  private static final double LEVEL_HEIGHT_RANGE = 0;
  private static final double SAFE_TO_LOWER_DOWN = 0.05, SAFE_TO_LOWER_UP = 0.4, SAFETY_RANGE = 0.05;
  private static final ArrayList<Tuple<Double, Double>> DANGER_ZONES = new ArrayList<Tuple<Double, Double>>();

  private static Elevator instance;

  // TODO: Add sendable chooser
  private ElevatorLevel m_level = ElevatorLevel.GROUND; 
  
  private SmartTalon m_main,
                     m_follower;
  private IEncoder m_encoder;
  private DoubleSolenoid m_braker;

  private int m_pistonChanges = 0;

  private Elevator() {
    DANGER_ZONES.add(new Tuple<>(SAFE_TO_LOWER_DOWN - SAFETY_RANGE, SAFE_TO_LOWER_UP + SAFETY_RANGE));
    
    m_main = new SmartTalon(Motor.Main);
    m_follower = new SmartTalon(Motor.Follower);
    m_follower.follow(m_main);
    m_encoder = new Taloncoder(Sensor.TicksPerMeter, m_main);
    m_braker = new DoubleSolenoid(2, Solenoid.Forward, Solenoid.Reverse);
    resetEncoder();
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
    setDefaultCommand(new BrakeElevator());
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
    m_braker.set(value);
    m_pistonChanges += m_braker.get() != value ? 1 : 0;
  }

  public void setPower(double power) {
    m_main.set(power);
  }

  public void resetEncoder() {
    m_encoder.reset();
  }

  public int getPistonChanges() {
    return m_pistonChanges;
  }

  public void update() {
    SmartDashboard.putString("Elevator::Command", getCurrentCommandName());

    for (ElevatorLevel level : ElevatorLevel.values()) {
      if (Math.abs(Elevator.getInstance().getHeight() - level.getHeight()) <= LEVEL_HEIGHT_RANGE) {
        Elevator.getInstance().setLevel(level);
        break;
      }
    }

    SmartDashboard.putNumber("Elevator::SolenoidChanges", m_pistonChanges);
  }
}
package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.utils.sensors.PressureSensor;
import edu.wpi.first.hal.CompressorJNI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.greenblitz.robotname.RobotMap.Pneumatics.*;
import edu.greenblitz.robotname.commands.pneumatics.ActivateCompressorBelow;

public class Pneumatics extends Subsystem {

  private static Pneumatics instance;

  private PressureSensor m_pressureSensor;
  private Compressor m_compressor;

  private Pneumatics() {
    m_pressureSensor = new PressureSensor(Sensor.Pressure);
    m_compressor = new Compressor();
  }

  public double getPressure() {
    return m_pressureSensor.getPressure();
  }

  public void setCompressor(boolean isActive) {
    if (isActive)
      m_compressor.start();
    else
      m_compressor.stop();;
  }

  public boolean isActive() {
    return m_compressor.enabled();
  }

  public static void init() {
    if (instance == null)
        instance = new Pneumatics();
}

public static Pneumatics getInstance() {
    if (instance == null)
        init();
    return instance;
}

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ActivateCompressorBelow(80));
  }

  public void update() {
    
  }
}
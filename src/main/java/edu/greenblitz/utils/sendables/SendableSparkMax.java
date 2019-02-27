package edu.greenblitz.utils.sendables;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SendableSparkMax extends CANSparkMax implements Sendable {
    private static final String DEFAULT_NAME = "SparkMax";
    private static final String DEFAULT_SUBSYSTEM = "Ungrouped";

    private String m_name;
    private String m_subsystem;

    public SendableSparkMax(int deviceID, MotorType type, String subsystem) {
        super(deviceID, type);
        this.m_subsystem = subsystem;
        m_name = DEFAULT_NAME + "-" + getDeviceId();
    }

    public SendableSparkMax(int deviceID, MotorType type) {
        this(deviceID, type, DEFAULT_SUBSYSTEM);
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public void setName(String name) {
        m_name = name;
    }

    @Override
    public String getSubsystem() {
        return m_subsystem;
    }

    @Override
    public void setSubsystem(String subsystem) {
        m_subsystem = subsystem;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("power", this::get, this::set);
        builder.setSmartDashboardType("Speed controller");
    }

    private double getPosition() {
        return getEncoder().getPosition();
    }

    private double getVelocity() {
        return getEncoder().getVelocity();
    }
}

package edu.greenblitz.robotname.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.greenblitz.robotname.RobotMap.RearPicker.Motor;
import edu.greenblitz.robotname.RobotMap.RearPicker.Sensor;
import edu.greenblitz.robotname.RobotMap.RearPicker.Solenoid;
import edu.greenblitz.robotname.commands.picker.PickByBumbers;
import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RearPicker extends Subsystem {

    private static RearPicker instance;

    private DoubleSolenoid m_piston;
    private CANSparkMax m_motor;
    private DigitalInput m_lowSwitch, m_highSwitch;

    private RearPicker() {
        m_piston = new DoubleSolenoid(3, Solenoid.Forward, Solenoid.Reverse);
        m_motor = new CANSparkMax(Motor.Picker, MotorType.kBrushless);
        m_lowSwitch = new DigitalInput(Sensor.LowSwitch);
        m_highSwitch = new DigitalInput(Sensor.HighSwitch);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new PickByBumbers());
    }

    public static void init() {
        if (instance == null)
            instance = new RearPicker();
    }

    public static RearPicker getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    public void setState(Value value) {
        if (m_piston.get() != value) Report.pneumaticsUsed(getName());
        m_piston.set(value);
    }


    public void setPower(double power) {
        m_motor.set(power);
    }

    public boolean isLowered() {
        return m_lowSwitch.get();
    }

    public boolean isRaised() {
        return m_highSwitch.get();
    }


    public void update() {
        SmartDashboard.putString("RearPicker::Command", getCurrentCommandName());
    }
}
package edu.greenblitz.robotname.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.robotname.RobotMap.Roller.Motor;
import edu.greenblitz.robotname.RobotMap.Roller.Solenoid;
import edu.greenblitz.robotname.commands.roller.HandleByElevator;
import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Roller extends Subsystem {
    private static Roller instance;

    private DoubleSolenoid m_piston;
    private SpeedController m_motor;

    private Roller() {
        m_piston = new DoubleSolenoid(Solenoid.Forward, Solenoid.Reverse);
        m_motor = new CANSparkMax(Motor.Roller, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setExtender(Value value) {
        if (m_piston.get() != value) Report.pneumaticsUsed(getName());
        m_piston.set(value);
    }

    public void extend() {
        setExtender(Value.kForward);
    }

    public void retract() {
        setExtender(Value.kReverse);
    }

    public Value getExtenderState() {
        return m_piston.get();
    }

    public void setPower(double power) {
        m_motor.set(power);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new HandleByElevator());
    }

    public static void init() {
        if (instance == null)
            instance = new Roller();
    }

    public static Roller getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    public void update() {
        SmartDashboard.putString("Roller::Command", getCurrentCommandName());
        SmartDashboard.putString("Roller::Extender", getExtenderState().name());
    }

}
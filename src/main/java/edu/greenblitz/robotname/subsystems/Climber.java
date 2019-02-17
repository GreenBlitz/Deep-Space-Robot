package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.RobotMap.Climber.Motor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem {

    private static Climber instance;

    private WPI_TalonSRX m_extender, m_wheels;

    private Climber() {
        m_extender = new WPI_TalonSRX(Motor.Extender);
        m_wheels = new WPI_TalonSRX(Motor.Wheels);
    }

    public void setExtender(double power) {
        m_extender.set(power);
    }

    public void setWheels(double power) {
        m_wheels.set(power);
    }

    @Override
    public void initDefaultCommand() {
        OI.getMainJoystick().A.whenPressed(null);
    }

    public static void init() {
        if (instance == null)
            instance = new Climber();
    }

    public static Climber getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    public void update() {
        SmartDashboard.putString("Climber::Command", getCurrentCommandName());
    }
}

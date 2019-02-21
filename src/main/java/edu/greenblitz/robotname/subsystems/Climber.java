package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.greenblitz.robotname.RobotMap.Climber.Motor;
import edu.greenblitz.robotname.commands.simple.climber.ClimberControl;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

public class Climber extends Subsystem {

    private static Logger logger = Logger.getLogger("climber");

    private static Climber instance;

    private WPI_TalonSRX m_extender, m_wheels;

    private Climber() {
        m_extender = new WPI_TalonSRX(Motor.Extender);
        m_wheels = new WPI_TalonSRX(Motor.Wheels);

        logger.info("instantiated");
    }

    public void extend(double power) {
        if (Math.abs(power - m_extender.get()) < 10E-7) {
            logger.fine("extending!");
        }
        logger.finest("extender: " + power);
        m_extender.set(power);
    }

    public void drive(double power) {
        logger.finest("wheels: " + power);
        m_wheels.set(power);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ClimberControl());
        // OI.getMainJoystick().A.whenPressed(null);
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

package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.robotname.RobotMap.Climber.Motor;
import edu.greenblitz.robotname.RobotMap.Climber.Sensor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber {

    private static Logger logger = Logger.getLogger("climber");
    private static Climber instance;

    public static void init() {
        Extender.init();
        Wheels.init();
        Big.init();

        instance = new Climber();
    }

    public static Climber getInstance() {
        return instance;
    }

    public static class Extender extends Subsystem {
        private static Extender instance;

        private static void init() {
            instance = new Extender();
        }

        private static Extender getInstance() {
            return instance;
        }

        private CANSparkMax m_extender;

        private Extender() {
            m_extender = new CANSparkMax(Motor.EXTENDER, MotorType.kBrushless);
            logger.info("extender: instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        private void update() {
            SmartDashboard.putString("Climber::Extender::Current Command", getCurrentCommandName());
        }

        public void extend(double power) {
            if (Math.abs(power - m_extender.get()) < 10E-7) {
                logger.finer("extending!");
            }
            logger.finest("extender: " + power);
            m_extender.set(power);
        }
    }

    public static class Wheels extends Subsystem {
        private static Wheels instance;

        private static void init() {
            instance = new Wheels();
        }

        private static Wheels getInstance() {
            return instance;
        }

        private WPI_TalonSRX m_wheels;

        private Wheels() {
            m_wheels = new WPI_TalonSRX(Motor.WHEELS);
            logger.info("wheels: instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        private void update() {
            SmartDashboard.putString("Climber::Wheels::Current Command", getCurrentCommandName());
        }

        public void drive(double power) {
            m_wheels.set(power);
            logger.finest("wheels: " + power);
        }
    }

    public static class Big extends Subsystem {

        private static Big instance;

        private static void init() {
            instance = new Big();
        }

        private static Big getInstance() {
            return instance;
        }

        private WPI_TalonSRX m_bigLeader;
        private WPI_TalonSRX m_bigFollower;
        private DigitalInput m_limitSwitch;

        private Big() {
            m_bigLeader = new WPI_TalonSRX(Motor.BIG_0);
            m_bigFollower = new WPI_TalonSRX(Motor.BIG_1);
            m_limitSwitch = new DigitalInput(Sensor.LIMIT_SWITCH);

            m_bigFollower.follow(m_bigLeader);

            logger.info("big: instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        public void higher(double power) {
            set(-power);
        }

        public void lower(double power) {
            set(power);
        }

        public boolean isActive() {
            return m_limitSwitch.get();
        }

        private void update() {
            SmartDashboard.putBoolean("Climber::BIG::Limit Switch", isActive());
            SmartDashboard.putString("Climber::BIG::Current Command", getCurrentCommandName());
        }

        private void set(double power) {
            if (isActive()) m_bigLeader.set(power);
            else m_bigLeader.set(0);
        }
    }

    private Extender m_extender;
    private Wheels m_wheels;
    private Big m_big;

    private Climber() {
        m_extender = Extender.getInstance();
        m_wheels = Wheels.getInstance();
        m_big = Big.getInstance();
    }

    public Extender getExtender() {
        return m_extender;
    }

    public Wheels getWheels() {
        return m_wheels;
    }

    public Big getBig() {
        return m_big;
    }

    public void update() {
        m_big.update();
        m_wheels.update();
        m_extender.update();
    }
}

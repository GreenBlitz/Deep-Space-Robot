package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.robotname.RobotMap.Climber.Motor;
import edu.greenblitz.robotname.RobotMap.Climber.Sensor;
import edu.greenblitz.utils.sendables.SendableSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber {

    private static Climber instance;

    public static void init() {
        if (instance == null) instance = new Climber();
    }

    public static Climber getInstance() {
        return instance;
    }
    private Logger logger;

    public class Extender extends Subsystem {
        private SendableSparkMax m_extender;

        private Extender() {
            super("Climber::Extender");

            m_extender = new SendableSparkMax(Motor.EXTENDER, MotorType.kBrushless);
            addChild(m_extender);

            logger.info("instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        public void extend(double power) {
            m_extender.set(power);
        }
    }

    public class Wheels extends Subsystem {
        private WPI_TalonSRX m_wheels;

        private Wheels() {
            super("Climber::Wheels");
            m_wheels = new WPI_TalonSRX(Motor.WHEELS);

            addChild(m_wheels);

            logger.info("instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        public void drive(double power) {
            m_wheels.set(power);
        }
    }

    public class Big extends Subsystem {
        private WPI_TalonSRX m_bigLeader;
        private TalonSRX m_bigFollower;
        private DigitalInput m_limitSwitch;

        private Big() {
            m_bigLeader = new WPI_TalonSRX(Motor.BIG_0);
            m_bigFollower = new TalonSRX(Motor.BIG_1);
            m_limitSwitch = new DigitalInput(Sensor.LIMIT_SWITCH);

            m_bigFollower.follow(m_bigLeader);

            addChild(m_bigLeader);
            addChild(m_limitSwitch);

            logger.info("instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        public void higher(double power) {
            if(isAtLimit())
                set(0);
            else
                set(-Math.abs(0.2*power));
        }

        public void lower(double power){
            if(isAtLimit())
                set(Math.abs(0.05*power));
            else
                set(Math.abs(0.2*power));
        }

        public void move(double power) {
            if (power >= 0)
                lower(power);
            else
                higher(power);
        }

        public boolean isAtLimit() {
            return m_limitSwitch.get();
        }

        @Override
        public void initSendable(SendableBuilder builder) {
            super.initSendable(builder);
            builder.addBooleanProperty("limit switch", this::isAtLimit, null);
        }

        private void set(double power) {
            if (isAtLimit()) m_bigLeader.set(power);
            else m_bigLeader.stopMotor();
        }
    }

    private Extender m_extender;
    private Wheels m_wheels;
    private Big m_big;

    private Climber() {
        logger = LogManager.getLogger(getClass());
        m_extender = new Extender();
        m_wheels = new Wheels();
        m_big = new Big();
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

    public Logger getLogger() {
        return logger;
    }
}

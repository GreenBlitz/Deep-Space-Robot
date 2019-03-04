package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.robotname.RobotMap.Climber.Motor;
import edu.greenblitz.robotname.RobotMap.Climber.Sensor;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.SparkEncoder;
import edu.greenblitz.utils.sendables.SendableSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
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

    public class Extender extends GBSubsystem {
        private static final int TICKS_PER_METER = 1;

        private SendableSparkMax m_extender;
        private IEncoder m_encoder;

        private Extender() {
            super("Climber::Extender");

            m_extender = new SendableSparkMax(Motor.EXTENDER, MotorType.kBrushless);
            m_encoder = new SparkEncoder(TICKS_PER_METER, m_extender);
            addChild(m_extender);
            m_extender.setName("Extender");

            logger.info("instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        public void extend(double power) {
            m_extender.set(power);
        }

        public void resetEncoder() {
            m_encoder.reset();
        }

        public double getHeight() {
            return m_encoder.getNormalizedTicks();
        }
    }

    public class Wheels extends GBSubsystem {
        private WPI_TalonSRX m_wheels;

        private Wheels() {
            super("Climber::Wheels");
            m_wheels = new WPI_TalonSRX(Motor.WHEELS);

            addChild(m_wheels);
            m_wheels.setName("wheels");

            logger.info("instantiated");
        }

        @Override
        protected void initDefaultCommand() {

        }

        public void drive(double power) {
//            if (getExtender().getHeight() > 0 || power > 0)
            m_wheels.set(power);
        }
    }

    public class Big extends GBSubsystem {
        public static final double ON_LIMIT_POWER = 0.2;
        public static final double MAX_POWER = 1;

        private WPI_TalonSRX m_bigLeader;
        private DigitalInput m_limitSwitch;

        private Big() {
            m_bigLeader = new WPI_TalonSRX(Motor.BIG);
            m_limitSwitch = new DigitalInput(Sensor.LIMIT_SWITCH);

            addChild(m_bigLeader);
            addChild(m_limitSwitch);

            logger.info("instantiated");
        }

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(null);
        }

        public void higher(double power) {
            set(clamp(power, 0, MAX_POWER));
        }

        public void lower(double power) {
            set(-clamp(power, 0, MAX_POWER));
        }

        public boolean isAtLimit() {
//            return m_limitSwitch.get();
            return false;
        }

        @Override
        public void initSendable(SendableBuilder builder) {
            super.initSendable(builder);
            builder.addBooleanProperty("limit switch", this::isAtLimit, null);
        }

        public void set(double power) {
//            if (isAtLimit()) {
//                m_bigLeader.set(clamp(power, -ON_LIMIT_POWER, ON_LIMIT_POWER));
//            } else {
            m_bigLeader.set(clamp(power, -MAX_POWER, MAX_POWER));
//            }
        }

        private double clamp(double power, double min, double max) {
            return Math.max(Math.min(power, max), min);
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
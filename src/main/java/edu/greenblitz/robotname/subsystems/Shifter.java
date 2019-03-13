package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.robotname.RobotMap.Shifter.Solenoid;
import edu.greenblitz.robotname.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.robotname.commands.simple.shifter.KeepShift;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.sendables.SendableDoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.greenblitz.robotname.RobotMap.Shifter.PCM;

/**
 * This class is in charge of the shifter subsystem of the robot.
 * This subsystem includes a DoubleSolenoid.
 * It is important to note that this subsystem is very reliant on the Chassis subsystem, as it changes the gear ratio of that subsystem.
 *
 * @see Chassis
 * @see DoubleSolenoid
 */

public class Shifter extends GBSubsystem {

    private static Shifter instance;

    private SendableDoubleSolenoid m_piston;
    private Gear m_currentShift = Gear.POWER;
    private Logger logger;

    /**
     * This constructor constructs the piston.
     */
    private Shifter() {
        logger = LogManager.getLogger(getClass());

        m_piston = new SendableDoubleSolenoid(PCM, Solenoid.FORWARD, Solenoid.REVERSE);

        addChild(m_piston);
        m_piston.setName("gear");

        logger.info("instantiated");
    }

    /**
     * This function creates a new instance of this class.
     */
    public static void init() {
        if (instance == null) instance = new Shifter();
    }

    /**
     * This function returns an instance of the class as long as it isn't null.
     *
     * @return The current instance of the class
     */
    public static Shifter getInstance() {
        return instance;
    }

    public void reset() {
        setShift(Gear.POWER);
    }

    /**
     * This is an enum that works based on the state of piston.
     * POWER - Piston is in a forward state.
     * SPEED - Piston is in a reverse state.
     */
    public enum Gear {
        POWER(DoubleSolenoid.Value.kForward),
        SPEED(DoubleSolenoid.Value.kReverse);

        private DoubleSolenoid.Value m_value;

        Gear(DoubleSolenoid.Value value) {
            m_value = value;
        }

        /**
         * This function returns the current value of the piston
         *
         * @return The current state of the piston (off/forward/reverse)
         */
        public DoubleSolenoid.Value getValue() {
            return m_value;
        }

        public boolean isSpeed() {
            return this == SPEED;
        }
    }

    /**
     * This function sets the state of the piston based on the value received.
     *
     * @param state A value based off of the Gear enum. This value is then set as the state the piston is in.
     */
    public void setShift(Gear state) {
        m_currentShift = state;
        if (m_piston.get() != state.getValue()) {
            Robot.getInstance().getReport().updatePneumaticsUsed(getName());
            logger.debug("shifted to {}", state);
        }
        m_piston.set(state.getValue());
        Chassis.getInstance().setTickPerMeter(state);
    }

    public void toggleShift() {
        setShift(getCurrentGear() == Gear.POWER ? Gear.SPEED : Gear.POWER);
    }

    /**
     * This function returns the current state of the piston through the Gear enum.
     *
     * @return The state of the piston through the Gear enum
     */
    public Gear getCurrentGear() {
        return m_currentShift;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new AutoChangeShift());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addStringProperty("gear", () -> getCurrentGear().name(), null);
    }

    public Logger getLogger() {
        return logger;
    }
}

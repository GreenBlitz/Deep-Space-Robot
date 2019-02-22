package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.RobotMap.Shifter.Solenoid;
import edu.greenblitz.robotname.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

/**
 * This class is in charge of the shifter subsystem of the robot.
 * This subsystem includes a DoubleSolenoid.
 * It is important to note that this subsystem is very reliant on the Chassis subsystem, as it changes the gear ratio of that subsystem.
 *
 * @see Chassis
 * @see DoubleSolenoid
 */

public class Shifter extends Subsystem {

    private static Logger logger = Logger.getLogger("shifter");

    private static Shifter instance;
    private DoubleSolenoid m_piston;
    private ShifterState m_currentShift = ShifterState.POWER;

    /**
     * This constructor constructs the piston.
     */
    private Shifter() {
        m_piston = new DoubleSolenoid(2, Solenoid.FORWARD, Solenoid.REVERSE);

        logger.info("instantiated");
    }

    /**
     * This function creates a new instance of this class.
     */
    public static void init() {
        instance = new Shifter();
    }

    /**
     * This function returns an instance of the class as long as it isn't null.
     *
     * @return The current instance of the class
     */
    public static Shifter getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    /**
     * This is an enum that works based on the state of piston.
     * POWER - Piston is in a forward state.
     * SPEED - Piston is in a reverse state.
     */
    public enum ShifterState {
        POWER(DoubleSolenoid.Value.kForward),
        SPEED(DoubleSolenoid.Value.kReverse);

        private DoubleSolenoid.Value m_value;

        ShifterState(DoubleSolenoid.Value value) {
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
    }

    /**
     * This function sets the state of the piston based on the value received.
     *
     * @param state A value based off of the ShifterState enum. This value is then set as the state the piston is in.
     */
    private void setShift(ShifterState state) {
        m_currentShift = state;
        if (m_piston.get() != state.getValue()) {
            Report.pneumaticsUsed(getName());
            logger.fine("gear: " + state);
        }
        m_piston.set(state.getValue());
    }

    public void toggleShift() {
        setShift(getCurrentShift() == ShifterState.POWER ? ShifterState.SPEED : ShifterState.POWER);
    }

    public void toSpeed() {
        setShift(ShifterState.SPEED);
    }

    public void toPower() {
        setShift(ShifterState.POWER);
    }

    /**
     * This function returns the current state of the piston through the ShifterState enum.
     *
     * @return The state of the piston through the ShifterState enum
     */
    public ShifterState getCurrentShift() {
        return m_currentShift;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new AutoChangeShift());
    }

    /**
     * This function updates the information from the subsystem when called by the robot.
     */
    public void update() {
        SmartDashboard.putString("Shifter::Shift", getCurrentShift().name());
        SmartDashboard.putString("Shifter::Command", getCurrentCommandName());
    }
}

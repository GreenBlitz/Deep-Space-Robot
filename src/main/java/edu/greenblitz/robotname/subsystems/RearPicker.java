package edu.greenblitz.robotname.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.greenblitz.robotname.RobotMap.RearPicker.Motor;
import edu.greenblitz.robotname.RobotMap.RearPicker.Sensor;
import edu.greenblitz.robotname.RobotMap.RearPicker.Solenoid;
import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

public class RearPicker extends Subsystem {

    private static Logger logger = Logger.getLogger("picker");

    private static RearPicker instance;

    private DoubleSolenoid m_piston;
    private CANSparkMax m_motor;
    private DigitalInput m_lowSwitch, m_highSwitch;

    private RearPicker() {
        m_piston = new DoubleSolenoid(3, Solenoid.Forward, Solenoid.Reverse);
        m_motor = new CANSparkMax(Motor.Picker, MotorType.kBrushless);
        m_lowSwitch = new DigitalInput(Sensor.LowSwitch);
        m_highSwitch = new DigitalInput(Sensor.HighSwitch);

        logger.info("instantiated");
    }

    public enum State {
        STAND(Value.kReverse),
        PICK(Value.kOff),
        HOLD(Value.kForward);

        public final Value value;

        State(Value value) {
            this.value = value;
        }
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(null);
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

    private void setState(State state) {
        if (m_piston.get() != state.value) {
            Report.pneumaticsUsed(getName());
            logger.fine("state: " + state);
        }
        m_piston.set(state.value);
    }

    public void pick() {
        setState(State.PICK);
    }

    public void stand() {
        setState(State.STAND);
    }

    public void hold() {
        setState(State.HOLD);
    }

    public void setPower(double power) {
        m_motor.set(power);
        logger.finest("power: " + power);
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
package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Optional;

public class ClimberProportionalExtendByJoystick extends JoystickCommand<Climber.Extender> implements PIDSource, PIDOutput {

    private static final double START_UPPER_LIMIT = 0.15;
    private static final double GOAL = -0.5;
    private static final double Kp = 5 / 17.0, Ki = 0, Kd = 0;

    private static final double PIDGET_MULTI = 8.5;
    private static final double MIN_MULTI = 0.15;

    private PIDController m_controller;

    public ClimberProportionalExtendByJoystick(SmartJoystick joystick) {
        super(Climber.getInstance().getExtender(), joystick);

        m_controller = new PIDController(Kp, Ki, Kd, this, this);
    }

    @Override
    protected void atInit() {
        m_controller.setSetpoint(GOAL * PIDGET_MULTI);
        m_controller.setOutputRange(-1, 1);
        m_controller.setAbsoluteTolerance(0);

        m_controller.enable();
    }

    @Override
    protected void execute() {
        m_controller.setSetpoint(SmartJoystick.Axis.LEFT_Y.getValue(joystick) < 0 && system.getHeight() > -START_UPPER_LIMIT ? 0 : GOAL * PIDGET_MULTI);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void atEnd() {
        m_controller.disable();
        system.extend(0);
    }

    @Override
    public void pidWrite(double output) {
        output = m_controller.getSetpoint() == 0 ? output : -output;
        double multi = Math.max(output, MIN_MULTI);
        system.extend(multi * -SmartJoystick.Axis.LEFT_Y.getValue(joystick));
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return Climber.getInstance().getExtender().getHeight() * PIDGET_MULTI;
    }
}
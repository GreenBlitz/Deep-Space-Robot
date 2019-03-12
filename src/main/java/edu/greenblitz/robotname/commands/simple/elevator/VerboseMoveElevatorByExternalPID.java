package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.ElevatorState;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

import java.util.Optional;

public class VerboseMoveElevatorByExternalPID extends SubsystemCommand<Elevator> {
    public static final double SLOW_DOWN_DISTANCE = 0.3;
    public static final double MAX_POWER_UP = 0.6;
    public static final double MAX_POWER_DOWN = -0.4;
    public static final double MIN_POWER = 0;
    public static final double UP_FF = 0.2;
    public static final double DOWN_FF = -0.2;

    private static final PIDObject PID_CONFIG = new PIDObject(SLOW_DOWN_DISTANCE / MAX_POWER_UP, 0, 0, 0);

    static {
        RemoteCSVTarget.initTarget("elevator_pid", "t", "PID in", "PID out", "height", "FF", "power");
    }

    private double m_height;
    private PIDController m_controller;
    private double m_toleranceBelow;
    private double m_toleranceAbove;
    private RemoteCSVTarget m_currentLogTarget = RemoteCSVTarget.getTarget("elevator_pid");


    public VerboseMoveElevatorByExternalPID(double height) {
        this(height, Elevator.LEVEL_HEIGHT_TOLERANCE, Elevator.LEVEL_HEIGHT_TOLERANCE);
    }

    public VerboseMoveElevatorByExternalPID(double height, double toleranceBelow, double toleranceAbove) {
        super(Elevator.getInstance());
        m_toleranceBelow = toleranceBelow;
        m_toleranceAbove = toleranceAbove;
        m_height = height;
    }

    @Override
    protected void atInit() {
        m_controller = new PIDController(PID_CONFIG, (goal, current) -> {
            double error = goal - current;
            if (error <= 0 && Math.abs(error) < m_toleranceBelow) return true;
            return error >= 0 && error < m_toleranceAbove;
        });
        m_controller.configure(system.getHeight(), m_height, MAX_POWER_DOWN, MAX_POWER_UP, MIN_POWER);
        m_controller.setGoal(m_height);
        m_controller.configureOutputLimits(MAX_POWER_DOWN, MAX_POWER_UP);
        system.brake(false);
    }

    @Override
    protected void execute() {
        var t = System.currentTimeMillis();
        var in = system.getHeight() - m_height;
        double out = m_controller.calculatePID(in);
        var height = system.getHeight();
        var ff = pickFF(out);
        var power = out + ff;
        SmartDashboard.putNumber("PID in", in);
        SmartDashboard.putNumber("PID out", out);
        SmartDashboard.putNumber("height", height);
        SmartDashboard.putNumber("FF", ff);
        SmartDashboard.putNumber("power", power);
        m_currentLogTarget.report(t, in, out, height, ff, power);
        set(out, pickFF(out));
    }

    private double pickFF(double current) {
        if (current > 0) return UP_FF;
        if (current < 0) return DOWN_FF;
        return 0;
    }

    @Override
    protected boolean isFinished() {
        return m_controller.isFinished(system.getHeight());
    }

    @Override
    protected void atEnd() {
        system.brake(true);
        system.stop();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(
                new State(ElevatorState.getStateByHeight(m_height),
                        null, null, null));
    }

    private void set(double power, double ff) {
        system.setCompensatedPower(power, ff);
    }
}

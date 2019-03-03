package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.ElevatorState;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

import java.util.Optional;

public class MoveElevatorByExternalPID extends SubsystemCommand<Elevator> {
    public static final double SLOW_DOWN_DISTANCE = 0.3;
    public static final double MAX_POWER_UP = 0.6;
    public static final double MAX_POWER_DOWN = -0.4;
    public static final double MIN_POWER = 0.2;

    private static final PIDObject PID_CONFIG = new PIDObject(MAX_POWER_UP / SLOW_DOWN_DISTANCE, 0, 0, 0);

    private double m_height;
    private PIDController m_controller;
    private double toleBelow;
    private double toleAbove;

    public MoveElevatorByExternalPID(double height) {
        this(height, Elevator.LEVEL_HEIGHT_TOLERANCE, Elevator.LEVEL_HEIGHT_TOLERANCE);
    }

    public MoveElevatorByExternalPID(double height, double toleranceBelow, double toleranceAbove) {
        super(Elevator.getInstance());
        toleBelow = toleranceBelow;
        toleAbove = toleranceAbove;
        m_height = height;
    }

    @Override
    protected void initialize() {
        m_controller = new PIDController(PID_CONFIG, (goal, current) -> {
            double error = goal - current;
            if (error <= 0 && Math.abs(error) < toleBelow) return true;
            return error >= 0 && error < toleAbove;
        });
        m_controller.configure(system.getHeight(), m_height, MAX_POWER_DOWN, MAX_POWER_UP, MIN_POWER);
        m_controller.setGoal(m_height);
        m_controller.configureOutputLimits(MAX_POWER_DOWN, MAX_POWER_UP);
        system.brake(false);
    }

    @Override
    protected void execute() {
        var in = system.getHeight() - m_height;
        double out = m_controller.calculatePID(in);
        var ff = 0.0;
        if (out > 0) ff = 0.2;
        if (out < 0) ff = -0.1;
        set(out, ff);
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
package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.ElevatorState;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

import java.util.Optional;

public class MoveElevatorByExternalPID extends SubsystemCommand<Elevator> {
    public static final double SLOW_DOWN_DISTANCE = 0.4;
    public static final double MAX_POWER = 0.6;
    public static final double MIN_POWER = -0.4;

    private static final PIDObject PID_CONFIG = new PIDObject(MAX_POWER / SLOW_DOWN_DISTANCE, 0, 0, 0);

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
            if (error >= 0 && error < toleAbove) return true;
            return false;
        });
        m_controller.configure(system.getHeight(), m_height, MIN_POWER, MAX_POWER, 0);
        m_controller.setGoal(m_height);
        m_controller.configureOutputLimits(MIN_POWER, MAX_POWER);
        system.brake(false);
    }

    @Override
    protected void execute() {
        var in = system.getHeight();
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
    protected void end() {
        system.brake(true);
        system.stop();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(
                new State(ElevatorState.closestTo(m_height),
                        null, null, null));
    }

    private void set(double power, double ff) {
        system.setCompensatedPower(power, ff);
    }
}
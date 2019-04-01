package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveElevatorByExternalPID extends SubsystemCommand<Elevator> {
    public static final double SLOW_DOWN_DISTANCE = 0.5;
    public static final double MAX_POWER_UP = 0.7;
    public static final double MAX_POWER_DOWN = -0.3;
    public static final double MIN_POWER = 0;
    public static final double UP_FF = 0.3;
    public static final double DOWN_FF = -0.1;

    public static final long TIMEOUT = 4000;

    private static final PIDObject PID_CONFIG = new PIDObject(MAX_POWER_UP / SLOW_DOWN_DISTANCE, 0, 0, 0);

    private Elevator.Level m_level;
    private PIDController m_controller;
    private double toleBelow;
    private double toleAbove;

    private long timeStart;


    public MoveElevatorByExternalPID(Elevator.Level level) {
        this(level, Elevator.LEVEL_HEIGHT_TOLERANCE, Elevator.LEVEL_HEIGHT_TOLERANCE);
    }

    public MoveElevatorByExternalPID(Elevator.Level level, double toleranceBelow, double toleranceAbove) {
        super(Elevator.getInstance());
        toleBelow = toleranceBelow;
        toleAbove = toleranceAbove;
        m_level = level;
    }

    @Override
    protected void atInit() {
        logger.debug("moving the elevator to {}", m_level);
        timeStart = System.currentTimeMillis();
        double height = m_level.heightByCurrentState();
        m_controller = new PIDController(PID_CONFIG, (goal, current) -> {
            double error = goal - current;
            if (error <= 0 && Math.abs(error) < toleBelow) return true;
            return error >= 0 && error < toleAbove;
        });
        m_controller.configure(system.getHeight(), height, MAX_POWER_DOWN, MAX_POWER_UP, MIN_POWER);
        m_controller.setGoal(height);
        m_controller.configureOutputLimits(MAX_POWER_DOWN, MAX_POWER_UP);
        system.brake(false);
    }

    @Override
    protected void execute() {
        var in = system.getHeight();
        double out = m_controller.calculatePID(in);
        SmartDashboard.putNumber("pid in", in);
        SmartDashboard.putNumber("pid out", out + pickFF(out));
        set(out, pickFF(out));
    }

    private double pickFF(double current) {
        if (current > 0) return UP_FF;
        if (current < 0) return DOWN_FF;
        return 0;
    }

    @Override
    protected boolean isFinished() {
        return m_controller.isFinished(system.getHeight()) ||
                System.currentTimeMillis() - timeStart > TIMEOUT;
    }

    @Override
    protected void atEnd() {
        system.brake(true);
        system.stop();
    }

    private void set(double power, double ff) {
        system.setCompensatedPower(power, ff);
    }
}
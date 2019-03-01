package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class AlignToVisionTarget extends ChassisBaseCommand {

    private static final double FULL_POWER = 0;
    private static final long TIME_ON_TARGET = 0;
    private static final PIDObject PID_CONFIG = new PIDObject(0, 0, 0);
    private static final ITolerance PID_TOLERANCE = new AbsoluteTolerance(0.1);

    private long m_onTarget = -1;

    private PIDController m_controller;

    public AlignToVisionTarget(){
        m_controller = new PIDController(PID_CONFIG, PID_TOLERANCE);
    }

    @Override
    public void initialize() {
        VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
        m_controller.setGoal(0);
        m_controller.configureOutputLimits(-FULL_POWER, FULL_POWER);
    }

    @Override
    protected void execute() {
        set(m_controller.calculatePID(get()));
    }

    @Override
    protected boolean isFinished() {
        if (m_controller.isFinished())
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
            else
                m_onTarget = -1;

      return m_controller.isFinished() && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
    }

    public void set(double output) {
        system.tankDrive(-output, output);
    }

    @Override
    public void end(){
        super.end();
        system.stop();
    }

    public double get() {
        return VisionMaster.getInstance().getStandardizedData().getCenterAngle();
    }
}
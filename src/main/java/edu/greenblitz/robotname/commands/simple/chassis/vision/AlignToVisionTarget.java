package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.hid.SmartJoystick;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class AlignToVisionTarget extends ChassisBaseCommand {

    private static final double FULL_POWER = 0.15;
    private static final double SLOWDOWN_ANGLE = 25;
    private static final long TIME_ON_TARGET = 200;
    private static final PIDObject PID_CONFIG = new PIDObject(FULL_POWER / SLOWDOWN_ANGLE, 0, 0);
    private static final ITolerance PID_TOLERANCE = new AbsoluteTolerance(3);

    private long m_onTarget = -1;

    private PIDController m_controller;

    public AlignToVisionTarget(){
        m_controller = new PIDController(PID_CONFIG, PID_TOLERANCE);
    }

    @Override
    public void atInit() {
        VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
        m_controller.configure(get(), 0, -FULL_POWER, FULL_POWER, 0);
    }

    @Override
    protected void execute() {
        system.arcadeDrive(SmartJoystick.Axis.LEFT_Y.getValue(OI.getMainJoystick()),
                           -m_controller.calculatePID(get()));
    }

    @Override
    protected boolean isFinished() {
//        if (m_controller.isFinished(get())) {
//            if (m_onTarget == -1)
//                m_onTarget = System.currentTimeMillis();
//        }
//        else
//            m_onTarget = -1;
//
//      return m_controller.isFinished(get()) && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
        return false;
    }

    public void set(double output) {

    }

    @Override
    protected void atEnd(){
        system.stop();
    }

    public double get() {
        return VisionMaster.getInstance().getStandardizedData()[0].getCenterAngle();
    }
}
package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;

public class TriggerDriveByVision extends ChassisBaseCommand {

    private static final double FULL_POWER = 0.25;
    private static final double SLOWDOWN_ANGLE = 50;
    private static final double TOLERANCE = 0.5; // In degrees
    private static final double DEADBAND = 0;
    private static final double kP = FULL_POWER / SLOWDOWN_ANGLE, Ki = 0, Kd = 0, kF = 0;

    private PIDController m_pid;

    public TriggerDriveByVision() {
        m_pid = new PIDController(new PIDObject(kP, Ki, Kd, kF),
                new AbsoluteTolerance(Math.toRadians(TOLERANCE)));
    }


    @Override
    protected void atInit() {
        m_pid.configure(VisionMaster.getInstance().getStandardizedData()[0].getCenterAngle(), 0,
                -FULL_POWER, FULL_POWER, DEADBAND);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        var angle = get();
        set(-m_pid.calculatePID(angle));
    }

    private double get() {
        return VisionMaster.getInstance().getStandardizedData()[0].getCenterAngle();
    }

    private void set(double output) {
        Chassis.getInstance().arcadeDrive(
                OI.getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y),
                output);
    }
}

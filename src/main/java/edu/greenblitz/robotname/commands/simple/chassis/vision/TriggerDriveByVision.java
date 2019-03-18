package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.GenericHID;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;

public class TriggerDriveByVision extends ChassisBaseCommand {

    private static final double FULL_POWER = 0.25;
    private static final double SLOWDOWN_ANGLE = 30;
    private static final double TOLERANCE = 10; // In degrees
    private static final double DEADBAND = 0;
    private static final double kP = FULL_POWER / SLOWDOWN_ANGLE, Ki = 0, Kd = 0, kF = 0;

    private PIDController m_pid;
    private SmartJoystick m_joystick;

    public TriggerDriveByVision(SmartJoystick joystick) {
        m_pid = new PIDController(new PIDObject(kP, Ki, Kd, kF),
                new AbsoluteTolerance(Math.toRadians(TOLERANCE)));
        m_joystick = joystick;
    }

    public TriggerDriveByVision() {
        this(OI.getMainJoystick());
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
        double leftRumble, rightRumble;
        var angle = get();
        var normalized = Math.abs(angle / SLOWDOWN_ANGLE);
        set(-m_pid.calculatePID(angle));

        if (angle > 0) {
            leftRumble = 0;
            rightRumble = normalized;
        } else if (angle < 0) {
            leftRumble = normalized;
            rightRumble = 0;
        } else {
            leftRumble = 0;
            rightRumble = 0;
        }

        m_joystick.rumble(true, rightRumble);
        m_joystick.rumble(false, leftRumble);
    }

    private double get() {
        return VisionMaster.getInstance().getStandardizedData()[0].getCenterAngle();
    }

    private void set(double output) {
        Chassis.getInstance().arcadeDrive(
                OI.getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y),
                output);
    }

    @Override
    protected void atEnd() {
        m_joystick.rumble(false, 0);
        m_joystick.rumble(true, 0);
    }
}

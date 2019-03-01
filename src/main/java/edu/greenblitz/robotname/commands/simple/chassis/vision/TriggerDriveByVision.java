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

    private PIDController m_pid;

    private static final double limitUpper = 0.3, limitAbs = 0.03;
    private static final double kP = 0.5*(2*limitUpper/Math.PI), Ki = 0, Kd = 0;

    public TriggerDriveByVision(){
        m_pid = new PIDController(new PIDObject(kP, Ki, Kd, 0),
                new AbsoluteTolerance(Math.toRadians(3.0)));
    }

    @Override
    protected void initialize(){
//        m_pid.configure(VisionMaster.getInstance().getStandardizedData().getCenterAngle(),0,
//                -limitUpper, limitUpper, limitAbs);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute(){
        System.out.println("angle = " + Math.toDegrees(VisionMaster.getInstance().getStandardizedData().getCenterAngle()));
        double output = -m_pid.calculatePID(VisionMaster.getInstance().getStandardizedData().getCenterAngle());
        Chassis.getInstance().arcadeDrive(OI.getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER),
                                        output);
    }

}

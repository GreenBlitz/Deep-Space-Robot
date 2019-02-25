package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.opencv.core.Mat;

public class TriggerDriveByVision extends SubsystemCommand<Chassis>  {

    private PIDController m_pid;


    private static final double limitUpper = 0.3, limitAbs = 0.06;
    private static final double kP = 0.2*(2*limitUpper/Math.PI), Ki = 0, Kd = 0;

    public TriggerDriveByVision(){
        super(Chassis.getInstance());
        m_pid = new PIDController(new PIDObject(kP, Ki, Kd, 0),
                new AbsoluteTolerance(Math.toRadians(3.0)));
    }

    @Override
    protected void initialize(){
        m_pid.configure(VisionMaster.getInstance().getStandardizedData().getCenterAngle(),0,
                -limitUpper, limitUpper, limitAbs);
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

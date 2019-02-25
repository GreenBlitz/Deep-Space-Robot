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

public class TriggerDriveByVision extends SubsystemCommand<Chassis>  {

    private org.greenblitz.motion.pid.PIDController m_pid;



    private static final double kP = 1, Ki = 0, Kd = 0;

    public TriggerDriveByVision(){
        super(Chassis.getInstance());
        m_pid = new PIDController(new PIDObject(kP,Ki,Kd));
    }

    @Override
    protected void initialize(){
        m_pid.configure(0,0,0,0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

//    @Override
//    public void pidWrite(double output) {
//        System.out.println("Wow driving - " + output);
//        Chassis.getInstance().arcadeDrive(OI.getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER),
//                                        output);
//    }
//
//    @Override
//    public void setPIDSourceType(PIDSourceType pidSource) {
//
//    }
//
//    @Override
//    public PIDSourceType getPIDSourceType() {
//        return PIDSourceType.kDisplacement;
//    }
//
//    @Override
//    public double pidGet() {
//        double angle = VisionMaster.getInstance().getStandardizedData().getCenterAngle();
//        System.out.println("NICE ANGLE - " + angle);
//        return angle;
//    }
}

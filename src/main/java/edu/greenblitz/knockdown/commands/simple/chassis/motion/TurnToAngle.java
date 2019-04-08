package edu.greenblitz.knockdown.commands.simple.chassis.motion;

import edu.greenblitz.knockdown.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Shifter;
import org.greenblitz.motion.base.Position;
import org.opencv.core.Mat;

public class TurnToAngle extends ChassisBaseCommand {

    private double target;
    private boolean isClockwise;
    private double power;
    private boolean stopAtEnd;
    private int rightMult;
    private double tolerance;
    private double tryVisionAngle;

    public TurnToAngle(double t, double p, boolean stop, double tolerance, double visionAngle){
        target = Math.toRadians(t);
        tryVisionAngle = Math.toRadians(visionAngle);
        power = p;
        stopAtEnd = stop;
        this.tolerance = Math.toRadians(tolerance);
    }

    public
    TurnToAngle(double t, double p, boolean stop, double tolerance){
        this(t, p, stop, tolerance, 0);
    }

    @Override
    protected void atInit() {
        Shifter.getInstance().setShift(Shifter.Gear.POWER);
        double rightMult = Math.signum(Position.normalizeAngle(target - Chassis.getInstance().getLocation().getAngle()));
        system.tankDrive(power * -rightMult, power * rightMult);
    }

    @Override
    protected void atEnd() {
        if (stopAtEnd)
            system.stop();
    }

    @Override
    protected boolean isFinished() {
        double error = Math.abs(Position.normalizeAngle(Chassis.getInstance().getLocation().getAngle() - target));
        return error <= tolerance
                || (error <= tryVisionAngle && VisionMaster.getInstance().isDataValid());
    }
}
package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.profiling.ActuatorLocation;
import org.greenblitz.motion.profiling.Profiler1D;
import org.greenblitz.motion.profiling.followers.FeedForwards1DFollower;

public class CheckMax  extends Command {

    private double power;
    private double previousAngle;
    private double previousVel;
    private long previousTime;
    private double maxV = 0;
    private double maxA = 0;

    public CheckMax (double power){
        this.power = power;
    }

    @Override
    public void initialize(){
        previousTime = System.nanoTime();
        previousAngle = Chassis.getInstance().getLocation().getAngle();
        previousVel = 0;
        Chassis.getInstance().tankDrive(-power,power);
    }

    @Override
    protected void execute() {
        long time = System.nanoTime();
        double angle = Chassis.getInstance().getLocation().getAngle();
        double V = Math.abs(angle - previousAngle)/(time - previousTime);
        maxV = Math.max(maxV,V);
        maxA = Math.max(maxA,(V - previousVel)/(time - previousTime));
        SmartDashboard.putNumber("MAX VELOCITY",maxV);
        SmartDashboard.putNumber("MAX ACCELERATION",maxA);
        previousTime = time;
        previousAngle = angle;
        previousVel = V;
    }

    @Override
    protected boolean isFinished() {
       return false;
    }
}

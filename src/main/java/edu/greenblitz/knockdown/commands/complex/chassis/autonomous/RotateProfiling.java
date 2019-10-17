package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import org.greenblitz.motion.profiling.ActuatorLocation;
import org.greenblitz.motion.profiling.Profiler1D;
import org.greenblitz.motion.profiling.followers.FeedForwards1DFollower;

public class RotateProfiling extends Command {

    private double target;
    private double kv, ka;
    private double maxV, maxA;
    private FeedForwards1DFollower follower;

    public RotateProfiling(double angle, double maxVel, double maxAcc){
        requires(Chassis.getInstance());
        target = angle;
        maxV = maxVel;
        maxA = maxAcc;
        kv = 1.0/maxVel;
        ka = 1.0/maxAcc;
    }

    @Override
    public void initialize(){
        follower = new FeedForwards1DFollower(
                Profiler1D.generateProfile(maxV, maxA, -maxA,
                        0, new ActuatorLocation(0, 0),
                        new ActuatorLocation(target, 0)),
                kv, ka);
        follower.init();
    }

    @Override
    protected void execute() {
        Chassis.getInstance().arcadeDrive(0, follower.run());
    }

    @Override
    protected boolean isFinished() {
        return follower.isFinished();
    }
}

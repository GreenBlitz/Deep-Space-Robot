package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.profiling.ActuatorLocation;
import org.greenblitz.motion.profiling.MotionProfile1D;
import org.greenblitz.motion.profiling.Profiler1D;
import org.greenblitz.motion.profiling.followers.FeedForwards1DFollower;

public class RotateProfiling extends Command {

    private double target;
    private double kv, ka;
    private double maxV, maxA;
    private FeedForwards1DFollower follower;
    private MotionProfile1D prof;
    private double maxPower;

    public RotateProfiling(double angle, double maxVel, double maxAcc) {
        this(angle, maxVel, maxAcc, 1);
    }

    public RotateProfiling(double angle, double maxVel, double maxAcc, double maxPower) {
        this(angle, maxVel, maxAcc, maxPower, 1.0, 1.0);
    }

    public RotateProfiling(double angle, double maxVel, double maxAcc, double maxPower, double vM, double aM) {
        requires(Chassis.getInstance());
        target = angle;
        maxV = maxVel;
        maxA = maxAcc;
        kv = vM / maxVel;
        ka = aM / maxAcc;
        this.maxPower = maxPower;
    }

    @Override
    public void initialize() {
        prof = Profiler1D.generateProfile(maxV, maxA, -maxA,
                0, new ActuatorLocation(0, 0),
                new ActuatorLocation(target, 0));
        follower = new FeedForwards1DFollower(
                prof,
                kv, ka);
        follower.init();
    }

    @Override
    protected void execute() {
        double v = follower.run();
        Chassis.getInstance().arcadeDrive(0,
                Math.min(Math.abs(v), maxPower) * Math.signum(v));
    }

    @Override
    protected boolean isFinished() {
        return follower.isFinished();
    }
}

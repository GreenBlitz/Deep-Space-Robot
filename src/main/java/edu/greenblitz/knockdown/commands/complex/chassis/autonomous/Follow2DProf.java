package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.profiling.ActuatorLocation;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.*;
import org.greenblitz.motion.profiling.followers.FFFollwer2D;
import org.greenblitz.motion.profiling.followers.FeedForwards1DFollower;

import java.util.ArrayList;

public class Follow2DProf extends Command {

    MotionProfile2D profile2D;
    FFFollwer2D follower;
    double linKv, linKa, angKv, angKa;
    double maxPower;

    public Follow2DProf(ArrayList<State> path, double j, double linMaxVel, double linMaxAcc,
                           double rotMaxVel, double rotMaxAcc,
                           double maxPower, double velMultLin, double accMultLin,
                           double velMulrRot, double accMyltRot) {
        requires(Chassis.getInstance());
        profile2D = ChassisProfiler2D.generateProfile(path, j, linMaxVel, rotMaxVel, linMaxAcc, rotMaxAcc);
        linKv = velMultLin / linMaxVel;
        linKa = accMultLin / linMaxAcc;
        angKv = velMulrRot / rotMaxVel;
        angKa = accMyltRot / rotMaxAcc;
        this.maxPower = maxPower;
    }

    @Override
    public void initialize() {
        follower = new FFFollwer2D(linKv, linKa, angKv, angKa, profile2D);
        follower.init();
    }

    @Override
    protected void execute() {
        Vector2D vals = follower.run();
        Chassis.getInstance().tankDrive(clamp(vals.getX()), clamp(vals.getY()));
    }

    double clamp(double val){
        return Math.min(Math.abs(val), maxPower) * Math.signum(val);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

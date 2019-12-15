package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.RobotMap;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.ActuatorLocation;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.*;
import org.greenblitz.motion.profiling.followers.FeedForwards1DFollower;
import org.greenblitz.motion.profiling.followers.PidFollower2D;

import java.util.ArrayList;
import java.util.List;

public class Follow2DProf extends Command {

    MotionProfile2D profile2D;
    PidFollower2D follower;
    double linKv, linKa;
    double maxPower;

    public Follow2DProf(List<State> path, double j, double linMaxVel, double linMaxAcc,
                           double rotMaxVel, double rotMaxAcc,
                           double maxPower, double velMultLin, double accMultLin,
                           double velMulrRot, double accMyltRot) {
        requires(Chassis.getInstance());
        long t0 = System.currentTimeMillis();
        profile2D = ChassisProfiler2D.generateProfile(path, j, linMaxVel, rotMaxVel, linMaxAcc, rotMaxAcc, 0,
                1f);
        System.out.println("Time for profiles = " + (System.currentTimeMillis() - t0)/1000.0);
        linKv = velMultLin / linMaxVel;
        linKa = accMultLin / linMaxAcc;
        this.maxPower = maxPower;
    }

    public Follow2DProf(List<Position> path, double j, double linMaxVel, double linMaxAcc,
                        double rotMaxVel, double rotMaxAcc,
                        double maxPower, double velMultLin, double accMultLin,
                        double velMulrRot, double accMyltRot, int fuckJava) {
        this(new ArrayList<>(), j, linMaxVel, linMaxAcc, rotMaxVel, rotMaxAcc, maxPower, velMultLin, accMultLin,
                velMulrRot, accMyltRot);
        ArrayList<State> temp = new ArrayList<>();
        for (Position pos : path){
            temp.add(new State(pos.getX(), pos.getY(), pos.getAngle()));
            System.out.println(temp.get(temp.size() - 1));
        }
        profile2D = ChassisProfiler2D.generateProfile(temp, j, linMaxVel, rotMaxVel, linMaxAcc, rotMaxAcc, 0, 0.1);
    }

    long t0;
    @Override
    public void initialize() {
        follower = new PidFollower2D(linKv, linKa, linKv, linKa,
                new PIDObject(linKv), new PIDObject(linKv), 1,
                RobotMap.Chassis.Data.WHEEL_BASE_RADIUS,
                profile2D);
        follower.init();
        Chassis.getInstance().toCoast();
        t0 = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        Vector2D vals = follower.run(Chassis.getInstance().getLeftVelocity(),
                Chassis.getInstance().getRightVelocity());

        Chassis.getInstance().tankDrive(maxPower*vals.getX(), maxPower*vals.getY());
    }

    @Override
    protected void end(){
        SmartDashboard.putString("End Location", Chassis.getInstance().getLocation().toString());
        Chassis.getInstance().toBrake();
        Chassis.getInstance().tankDrive(0,0);
    }

    double clamp(double val){
        return Math.min(Math.abs(val), maxPower) * Math.signum(val);
    }

    @Override
    protected boolean isFinished() {
        return follower.isFinished();
    }
}

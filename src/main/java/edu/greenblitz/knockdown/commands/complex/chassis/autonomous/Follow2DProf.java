package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

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
    // constants for the motor equation: power = Kv * Vel + Ka * Acc
    double linKv, linKa, angKv, angKa;
    double maxPower;

    /**
     *
     * @param path the path to be followed
     * @param j jump
     * @param linMaxVel linear maximal velocity
     * @param linMaxAcc linear maximal acceleration
     * @param rotMaxVel rotational maximal velocity
     * @param rotMaxAcc rotational maximal acceleration
     * @param maxPower power limit
     * @param velMultLin gives control over the linear Kv constant in the motor equation for minor changes
     * @param accMultLin " Ka "
     * @param velMulrRot " rotational Kv "
     * @param accMyltRot " Ka "
     */

    public Follow2DProf(List<State> path, double j, double linMaxVel, double linMaxAcc,
                           double rotMaxVel, double rotMaxAcc,
                           double maxPower, double velMultLin, double accMultLin,
                           double velMulrRot, double accMyltRot) {
        requires(Chassis.getInstance());
        profile2D = ChassisProfiler2D.generateProfile(path, j, linMaxVel, rotMaxVel, linMaxAcc, rotMaxAcc, 0,
                0.8f);
        linKv = velMultLin / linMaxVel;
        linKa = accMultLin / linMaxAcc;
        angKv = velMulrRot / rotMaxVel;
        angKa = accMyltRot / rotMaxAcc;
        this.maxPower = maxPower;
    }

    /**
     *
     * @param path Position instead of State contains less info
     * @param j
     * @param linMaxVel
     * @param linMaxAcc
     * @param rotMaxVel
     * @param rotMaxAcc
     * @param maxPower
     * @param velMultLin
     * @param accMultLin
     * @param velMulrRot
     * @param accMyltRot
     * @param fuckJava Fuck Java (otherwise the constructor is not valid...)
     */
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

    /**
     * creates the pid follower and init it
     */
    @Override
    public void initialize() {
        follower = new PidFollower2D(linKv, linKa, angKv, angKa,
                new PIDObject(linKv*0), new PIDObject(linKv*0), 0,
                profile2D);
        follower.init();
        t0 = System.currentTimeMillis();
    }

    /**
     * receive the pid values corresponding to current velocity
     * print location and predicted location
     * drive
     */
    @Override
    public void execute() {
        Vector2D vals = follower.run(Chassis.getInstance().getLeftVelocity(),
                Chassis.getInstance().getRightVelocity());

        if (!follower.isFinished()) {
            double dt = (System.currentTimeMillis() - t0) / 1000.0;
            Position act = profile2D.getActualLocation(dt, 0.01);
            SmartDashboard.putNumber("Lin loc", profile2D.getLocation(dt).getX());
            SmartDashboard.putNumber("Actual x", act.getX());
            SmartDashboard.putNumber("Actual y", act.getY());
        }

        Chassis.getInstance().tankDrive(maxPower*vals.getX(), maxPower*vals.getY());
    }

    /**
     * stops robot
     */
    @Override
    protected void end(){
        Chassis.getInstance().tankDrive(0,0);
    }

    /**
     * @param val potential value for power
     * @return a valid value for power, closest to val
     */
    double clamp(double val){
        return Math.min(Math.abs(val), maxPower) * Math.signum(val);
    }

    /**
     * @return if follower finished
     */
    @Override
    public boolean isFinished() {
        return follower.isFinished();
    }
}

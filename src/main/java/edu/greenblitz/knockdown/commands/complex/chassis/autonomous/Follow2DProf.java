package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.gblib.threading.IThreadable;
import edu.greenblitz.knockdown.RobotMap;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.*;
import org.greenblitz.motion.profiling.followers.PidFollower2D;

import java.util.ArrayList;
import java.util.List;

public class Follow2DProf implements IThreadable {

    private MotionProfile2D profile2D;
    private PidFollower2D follower;
    private double linKv, linKa;
    private PIDObject o;
    private double d;

    private double maxPower;
    private boolean isOpp;
    private RemoteCSVTarget targetPath;

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
     */

    public Follow2DProf(List<State> path, double j, double linMaxVel, double linMaxAcc,
                           double rotMaxVel, double rotMaxAcc,
                           double maxPower, double velMultLin, double accMultLin,
                        PIDObject po, double tol, boolean isOpp) {
        long t0 = System.currentTimeMillis();
        profile2D = ChassisProfiler2D.generateProfile(path, j, linMaxVel, rotMaxVel, linMaxAcc, rotMaxAcc,
                0, 1.0, 1000);
        System.out.println("Time for profiles = " + (System.currentTimeMillis() - t0)/1000.0);
        linKv = velMultLin / linMaxVel;
        linKa = accMultLin / linMaxAcc;
        o = po;
        d = tol;
        this.isOpp = isOpp;
        this.maxPower = maxPower;
    }

    long t0;

    private long runTStart;
    private long minRuntime = 10;

    @Override
    public void run() {
        runTStart = System.currentTimeMillis();

        Vector2D vals = follower.run(Chassis.getInstance().getLeftVelocity(),
                Chassis.getInstance().getRightVelocity());

        if (isOpp){
            vals = vals.scale(-1);
        }

        Chassis.getInstance().tankDrive(maxPower*clamp(vals.getX()),
                maxPower*clamp(vals.getY()));

        targetPath.report(Chassis.getInstance().getLocation().getX(),
                Chassis.getInstance().getLocation().getY());

        while (System.currentTimeMillis() - runTStart < minRuntime) {}
    }

    public double clamp(double in){
        return Math.signum(in) * Math.min(Math.abs(in), 1);
    }

    /**
     * @return if follower finished
     */
    @Override
    public boolean isFinished() {
        return follower.isFinished();
    }

    @Override
    public void atEnd() {
        SmartDashboard.putString("End Location", Chassis.getInstance().getLocation().toString());
        Chassis.getInstance().toBrake();
        Chassis.getInstance().tankDrive(0,0);
    }

    @Override
    public void atInit() {
        follower = new PidFollower2D(linKv, linKa, linKv, linKa,
                o,
                d, 1,
                RobotMap.Chassis.Data.WHEEL_BASE_RADIUS,
                profile2D);
        follower.setSendData(true);
        follower.init();
        Chassis.getInstance().toCoast();
        t0 = System.currentTimeMillis();
        targetPath = RemoteCSVTarget.initTarget("ProfilePath", "x", "y");
    }
}

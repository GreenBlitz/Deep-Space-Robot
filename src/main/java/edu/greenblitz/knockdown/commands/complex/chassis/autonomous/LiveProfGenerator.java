package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.gblib.threading.IThreadable;
import edu.greenblitz.knockdown.RobotMap;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.ChassisProfiler2D;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.followers.PidFollower2D;

import java.util.ArrayList;
import java.util.List;

public class LiveProfGenerator implements IThreadable {


    private MotionProfile2D profile2D;
    private PidFollower2D follower;
    private double linKv, linKa;
    private PIDObject o;
    private double d;

    private PIDObject angOb;

    private State end;
    private double vMax, aMax, omMax, alMax;
    private double j;

    private double maxPower;
    private boolean isOpp;
    private RemoteCSVTarget targetPath;

    /**
     *
     * @param end the path to be followed
     * @param j jump
     * @param linMaxVel linear maximal velocity
     * @param linMaxAcc linear maximal acceleration
     * @param rotMaxVel rotational maximal velocity
     * @param rotMaxAcc rotational maximal acceleration
     * @param maxPower power limit
     * @param velMultLin gives control over the linear Kv constant in the motor equation for minor changes
     * @param accMultLin " Ka "
     */

    public LiveProfGenerator(State end, double j, double linMaxVel, double linMaxAcc,
                        double rotMaxVel, double rotMaxAcc,
                        double maxPower, double velMultLin, double accMultLin,
                        PIDObject po, double tol, PIDObject ob, boolean isOpp) {
        vMax = linMaxVel;
        aMax = linMaxAcc;
        omMax = rotMaxVel;
        alMax = rotMaxAcc;
        linKv = velMultLin / linMaxVel;
        linKa = accMultLin / linMaxAcc;
        this.j = j;
        this.end = end;
        o = po;
        d = tol;
        angOb = ob;
        this.isOpp = isOpp;
        this.maxPower = maxPower;
    }

    long t0;

    /**
     * @param in potential value for power
     * @return a valid value for power, closest to val
     */
    public double clamp(double in){
        return Math.signum(in) * Math.min(Math.abs(in), 1);
    }

    private long runTStart;
    private long minRuntime = 8;

    @Override
    public void run() {
        runTStart = System.currentTimeMillis();
        double mult = isOpp ? -1 : 1;

        Vector2D vals = follower.run(mult * Chassis.getInstance().getLeftVelocity(),
                mult * Chassis.getInstance().getRightVelocity(),
                mult * -Math.toRadians(Chassis.getInstance().getNavx().getRate()));

        if (isOpp){
            vals = vals.scale(-1);
        }

        Chassis.getInstance().tankDrive(maxPower*clamp(vals.getX()),
                maxPower*clamp(vals.getY()));

        targetPath.report(Chassis.getInstance().getLocation().getX(),
                Chassis.getInstance().getLocation().getY());

        while (System.currentTimeMillis() - runTStart < minRuntime) {}
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
        List<State> s = new ArrayList<>();
        SmartDashboard.putString("StartLoc", Chassis.getInstance().getLocation().toString());
        s.add(new State(Chassis.getInstance().getLocation(),
                Chassis.getInstance().getLocation().getAngle()));
        s.add(end);
        profile2D = ChassisProfiler2D.generateProfile(s, j,
                vMax, omMax,
                aMax, alMax,
                0, 1.0, 100);
        follower = new PidFollower2D(linKv, linKa, linKv, linKa,
                o,
                d, 1, angOb, 0.01*omMax,
                RobotMap.Chassis.Data.WHEEL_BASE_RADIUS,
                profile2D);
        follower.setSendData(true);
        follower.init();
        Chassis.getInstance().toCoast();
        t0 = System.currentTimeMillis();
        targetPath = RemoteCSVTarget.initTarget("ProfilePath", "x", "y");
    }

}

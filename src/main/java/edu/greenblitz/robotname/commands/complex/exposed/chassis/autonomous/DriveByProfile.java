package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.profiling.ChassisProfiler2D;
import org.greenblitz.motion.profiling.MotionProfile2D;
import edu.greenblitz.robotname.subsystems.Chassis;

import java.util.Arrays;
import java.util.List;

public class DriveByProfile extends ChassisBaseCommand {

    private long startTime;
    private MotionProfile2D profile;
    private boolean isFinished;

    private double t, limitor;

    private static final double MAX_VEL = 0.3; //3.2;
    private static final double MAX_ACCEL = 789; //42.6951911765/2;
    private static final double MAX_ANG_VEL = 0;
    private static final double MAX_ANG_ACCEL = 0;
    private static final double ONE_THOUSANDTH = 0.001;

    private static final double kV = 0.2, //1.089 * (1 / MAX_VEL),
            kA = 1, //0.7*(1/MAX_ACCEL * 0.5),
            kVA = 0.2,
            kAA = 1;
//            kP = 0;

//    private PIDController locationController;

    private final Chassis m_chasis;

    public DriveByProfile(State... locs) {
        this(Arrays.asList(locs));
        System.out.println("constructor");
    }

    public DriveByProfile(List<State> locs) {
//        locationController = new PIDController(new PIDObject(kP));
        profile = ChassisProfiler2D.generateProfile(locs, 0, 0.05, kV, kA, kVA, kAA);//kv, ka... should not be passed here. this was written in 2 AM and should die
        m_chasis = Chassis.getInstance();
        logger.debug(profile.getTEnd());
    }

    @Override
    protected void initialize() {

//        locationController.setGoal(0);
        drive(0, 0);

        startTime = System.currentTimeMillis();

        t = 0.0;
        limitor = 0;
    }

    @Override
    protected void execute() {
        t = (System.currentTimeMillis() - startTime) * ONE_THOUSANDTH;

//        Vector2D loc = profile.getLocation(t);
        Vector2D vel = profile.getVelocity(t);
        Vector2D acc = profile.getAcceleration(t);

        isFinished = profile.isOver(t);
        if (isFinished) {
            System.out.println("------------------");
//            System.out.printf(
//                    "t: %f\nposition: expected: %f\n          actual:   %f\nvelocity: expected: %f\n          actual:   %f\n",
//                    t,
//                    profile.getLocation(profile.getTEnd()),
//                    getLoction(),
//                    profile.getVelocitySquared(profile.getTEnd()),
//                    getActualSpeed());
            return;
        }

        System.out.println(t);
        double vPower = kV * vel.getY();
        double aPower = kA * acc.getY();
        double angVPower = kVA * vel.getX();
        double angAPower = kAA * acc.getX();

        //linearPower = vPower + aPower + ff + locationController.calculate(profile.getLocation(t), prototype.getDistance());
        double linearPower = vPower + aPower;
        double angularPower = angVPower + angAPower;

        drive(linearPower, angularPower);

//        if (t >= limitor) {
//            System.out.println("------------------");
//            System.out.printf(
//                    "t: %f\nposition: expected: %f\n          actual:   %f\nvelocity: expected: %f\n          actual:   %f\n",
//                    t,
//                    loc,
//                    getLoction(),
//                    vel,
//                    getActualSpeed());
//
//            limitor += Math.max(t-limitor, 0.1);
//        }

//            if (printerVel != null)
//                printerVel.report(t, profile.getVelocitySquared(t), prototype.getSpeed());
//            if (printerLoc != null)
//                printerLoc.report(t, profile.getLocation(t), prototype.getDistance());
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    protected Point getLoction() {
        return m_chasis.getLocation();
    }

    protected void drive(double power, double angPower) {
        logger.debug("power = {}, angPower = {}", power, angPower);
        m_chasis.arcadeDrive(power, angPower);
    }
}

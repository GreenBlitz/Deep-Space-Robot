package edu.greenblitz.knockdown.commands.simple.chassis;

import com.kauailabs.navx.frc.AHRS;
import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.subsystems.Shifter;

/**
 * This falls *FORWARDS*
 */
public class FallWithNavx extends ChassisBaseCommand {

    private AHRS navx;
    private boolean startedFalling;

    private static final double CRITICAL_ANGLE_START = 6;
    private static final double CRITICAL_ANGLE_STOP = 4;
    private static final double POWER = 0.4;
    // This timeout is hand made for a really good reason, ask Alexey
    private static final long TIMEOUT = 2000;
    private long startTime;
//    private RemoteCSVTarget target;
    private double ground;

    @Override
    protected void atInit(){
        startTime = System.currentTimeMillis();
        startedFalling = false;
        Shifter.getInstance().setShift(Shifter.Gear.POWER);
//        target = RemoteCSVTarget.initTarget("AutoFall", "Pitch", "startedFalling", "startedFalling");
        navx = system.getNavx();
        navx.resetDisplacement();
        ground = navx.getPitch();
    }

    @Override
    protected void execute(){
        system.tankDrive(POWER, POWER);
//        target.report(navx.getPitch() - ground, isFalling() ? 1 : 0, startedFalling ? 1 : 0);
        if (isFalling())
            startedFalling = true;
    }

    private boolean isFalling(){
        return (!startedFalling && Math.abs(navx.getPitch()) > CRITICAL_ANGLE_START - ground) ||
                (startedFalling && Math.abs(navx.getPitch()) > CRITICAL_ANGLE_STOP - ground);
    }

    @Override
    protected void atEnd(){
//        long dt = System.currentTimeMillis() - startTime;
//        logger.debug("FallWithNavx FINISHED AT {} MS - {} S", dt, dt/1000.0);
        if (System.currentTimeMillis() - startTime > TIMEOUT){
//            logger.debug("FallWithNavx reached timeout, switching to driver.");
            new ArcadeDriveByJoystick(OI.getMainJoystick()).start();
        }
    }

    @Override
    protected boolean isFinished() {
        return (startedFalling && !isFalling())
        || System.currentTimeMillis() - startTime > TIMEOUT;
    }
}

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

    @Override
    protected void atInit(){
        startTime = System.currentTimeMillis();
        startedFalling = false;
        Shifter.getInstance().setShift(Shifter.Gear.POWER);
        navx = system.get_navx();
        navx.resetDisplacement();
    }

    @Override
    protected void execute(){
        system.tankDrive(POWER, POWER);
        if (isFalling())
            startedFalling = true;
    }

    private boolean isFalling(){
        return (!startedFalling && Math.abs(navx.getPitch()) > CRITICAL_ANGLE_START) ||
                (startedFalling && Math.abs(navx.getPitch()) > CRITICAL_ANGLE_STOP);
    }

    @Override
    protected void atEnd(){
        long dt = System.currentTimeMillis() - startTime;
        logger.debug("FallWithNavx FINISHED AT {} MS - {} S", dt, dt/1000.0);
        if (System.currentTimeMillis() - startTime > TIMEOUT){
            logger.debug("FallWithNavx reached timeout, switching to driver.");
            new ArcadeDriveByJoystick(OI.getMainJoystick()).start();
        }
    }

    @Override
    protected boolean isFinished() {
        return (startedFalling && !isFalling())
        || System.currentTimeMillis() - startTime > TIMEOUT;
    }
}

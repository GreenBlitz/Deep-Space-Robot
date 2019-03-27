package edu.greenblitz.robotname.commands.simple.chassis;

import com.kauailabs.navx.frc.AHRS;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Position;

public class FallWithNavx extends ChassisBaseCommand {

    private AHRS navx;
    private boolean startedFalling;

    private static final double CRITICAL_ANGLE_START = 10;
    private static final double CRITICAL_ANGLE_STOP = 6;
    private static final double POWER = -0.5;

    @Override
    protected void atInit(){
        startedFalling = false;
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
        Position currentLoc = Localizer.getInstance().getLocation();
        // TODO test I use the correct values
        currentLoc.translate(-navx.getDisplacementX(), navx.getDisplacementY());
        currentLoc.setAngle(-Math.toRadians(navx.getAngle()));
        new ResetLocalizer(currentLoc).start();
    }

    @Override
    protected boolean isFinished() {
        return startedFalling && !isFalling();
    }
}

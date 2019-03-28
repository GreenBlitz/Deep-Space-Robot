package edu.greenblitz.robotname.commands.simple.chassis;

import com.kauailabs.navx.frc.AHRS;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Position;

/**
 * This falls *FORWARDS*
 */
public class FallWithNavx extends ChassisBaseCommand {

    private AHRS navx;
    private boolean startedFalling;
    private Position startLocation;

    private static final double CRITICAL_ANGLE_START = 10;
    private static final double CRITICAL_ANGLE_STOP = 8;
    private static final double POWER = 0.2;

    @Override
    protected void atInit(){
        startedFalling = false;
        Shifter.getInstance().setShift(Shifter.Gear.POWER);
        navx = system.get_navx();
        startLocation = Localizer.getInstance().getLocation();
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
        // TODO test I use the correct values
        startLocation.translate(-navx.getDisplacementX(), navx.getDisplacementY());
        startLocation.setAngle(-Math.toRadians(navx.getAngle()));
        new ResetLocalizer(startLocation).start();
    }

    @Override
    protected boolean isFinished() {
        return startedFalling && !isFalling();
    }
}

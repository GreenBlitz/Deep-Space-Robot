package edu.greenblitz.knockdown.commands.simple.chassis;

import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ArcadeUntilVision extends ArcadeDriveByJoystick {
    public ArcadeUntilVision(SmartJoystick joystick) {
        super(joystick);
    }

    public ArcadeUntilVision() {
        this(OI.getMainJoystick());
    }

    @Override
    protected void atEnd() {
        Chassis.getInstance().setRampRate(0);
    }

    @Override
    protected boolean isFinished() {
        return VisionMaster.getInstance().isDataValid();
    }
}

package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.data.vision.VisionMaster;
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
    }

    @Override
    protected boolean isFinished() {
        return VisionMaster.getInstance().isDataValid();
    }
}

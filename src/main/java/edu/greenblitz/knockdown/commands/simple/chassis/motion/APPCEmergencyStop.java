package edu.greenblitz.knockdown.commands.simple.chassis.motion;

import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class APPCEmergencyStop extends ChassisBaseCommand {

    private static final long TIMEOUT = 1000;

    private long tStart;

    @Override
    protected void atInit() {
        tStart = System.currentTimeMillis();
    }

    @Override
    protected void atEnd() {
        Chassis.getInstance().toBrake();
        new ArcadeDriveByJoystick(OI.getMainJoystick()).start();
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() - tStart > TIMEOUT;
    }
}

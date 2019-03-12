/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.chassis;

public class StopChassis extends ChassisBaseCommand {

    private static final double VELOCITY_THRESHOLD = 0.1;

    @Override
    protected void atInit() {
        system.toBrake();
        system.stop();
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(system.getVelocity()) < VELOCITY_THRESHOLD;
    }

}

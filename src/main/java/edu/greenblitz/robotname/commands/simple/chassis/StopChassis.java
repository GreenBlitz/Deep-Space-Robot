/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.subsystems.Chassis;

public class StopChassis extends ChassisBaseCommand {

    @Override
    protected void execute() {
        Chassis.getInstance().stop();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}

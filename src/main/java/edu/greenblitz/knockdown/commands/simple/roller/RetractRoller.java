/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.knockdown.commands.simple.roller;

public class RetractRoller extends RollerBaseCommand {
    public RetractRoller() {
    }

    public RetractRoller(long ms) {
        super(ms);
    }

    @Override
    protected void atInit() {
        system.retract();
    }
}

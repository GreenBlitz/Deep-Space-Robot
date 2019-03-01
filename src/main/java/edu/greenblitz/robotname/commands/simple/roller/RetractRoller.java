/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RetractRoller extends RollerBaseCommand {
    private static final long ROLLER_RETRACTION_TIMEOUT = 1000;

    public RetractRoller() {
        super(ROLLER_RETRACTION_TIMEOUT);
    }

    @Override
    protected RollerState getNextState() {
        return RollerState.ROLLER_IN;
    }

    @Override
    protected void initialize() {
        system.retract();
    }
}

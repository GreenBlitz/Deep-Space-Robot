/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.data.sm.RollerState;

public class ExtendRoller extends RollerBaseCommand {
    private static final long ROLLER_EXTENSION_TIMEOUT = 2000;

    public ExtendRoller() {
        super(ROLLER_EXTENSION_TIMEOUT);
    }

    @Override
    protected RollerState getNextState() {
        return RollerState.ROLLER_OUT;
    }

    @Override
    protected void initialize() {
        system.extend();
    }
}

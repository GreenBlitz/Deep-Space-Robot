/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.RollerState;
import edu.greenblitz.utils.sm.State;

public class ExtendRoller extends TimedSubsystemCommand<Roller> {
    private static final long ROLLER_EXTENSION_TIMEOUT = 2000;

    public ExtendRoller() {
        super(ROLLER_EXTENSION_TIMEOUT, Roller.getInstance());
    }

    @Override
    public State getDeltaState() {
        return new State(null, RollerState.ROLLER_OUT, null, null);
    }

    @Override
    protected void initialize() {
        system.extend();
    }
}

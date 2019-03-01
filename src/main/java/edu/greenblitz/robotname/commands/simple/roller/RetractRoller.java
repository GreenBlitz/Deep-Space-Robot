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

public class RetractRoller extends TimedSubsystemCommand<Roller> {
    private static final long ROLLER_RETRACTION_TIMEOUT = 1000;

    public RetractRoller() {
        super(ROLLER_RETRACTION_TIMEOUT, Roller.getInstance());
    }

    @Override
    public State getDeltaState() {
        return new State(null, RollerState.ROLLER_IN, null, null);
    }

    @Override
    protected void initialize() {
        system.retract();
    }
}

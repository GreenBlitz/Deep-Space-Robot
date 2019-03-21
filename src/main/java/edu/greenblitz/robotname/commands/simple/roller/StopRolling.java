/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class StopRolling extends SubsystemCommand<Roller> {

    public StopRolling(long ms) {
        super(ms, Roller.getInstance());
    }

    public StopRolling() {
        super(Roller.getInstance());
    }

    @Override
    protected void atInit() {
        system.stopRolling();
    }

    protected boolean isFinished() {
        return true;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.RollerState;
import edu.greenblitz.utils.sm.State;

public class RollIn extends SubsystemCommand<Roller> {
    public RollIn() {
        super(Roller.getInstance());
    }

    @Override
    protected void execute() {
        system.rollIn();
    }

    protected boolean isFinished() {
        return false;
    }

    @Override
    public State getDeltaState() {
        return new State(null, RollerState.ROLLER_IN, null, null);
    }

    @Override
    protected void end() {
        super.end();
        system.stop();
    }
}

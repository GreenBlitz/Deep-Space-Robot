/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.data.sm.State;

import java.util.Optional;

public class RollOut extends SubsystemCommand<Roller> {
    public RollOut() {
        super(Roller.getInstance());
    }

    @Override
    protected void execute() {
        system.rollOut();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void end() {
        super.end();
        system.stop();
    }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;

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
    protected void end() {
        system.stop();
    }
}
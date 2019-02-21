/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;

public class RollCargoIn extends SubsystemCommand<Roller> {
    public RollCargoIn() {
        super(Roller.getInstance());
    }

    @Override
    protected void execute() {
        system.setPower(1);
    }

    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Roller.getInstance().setPower(0);
    }
}

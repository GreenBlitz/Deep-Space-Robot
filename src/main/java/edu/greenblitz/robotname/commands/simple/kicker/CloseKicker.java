/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class CloseKicker extends SubsystemCommand<Kicker> {

    public CloseKicker() {
        super(Kicker.getInstance());
    }

    @Override
    protected void execute() {
        system.unkick();
    }

    @Override
    protected boolean isFinished() {
        return system.isClosed();
    }

}

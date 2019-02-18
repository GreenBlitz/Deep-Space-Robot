/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.picker;

import edu.greenblitz.robotname.subsystems.RearPicker;
import edu.greenblitz.utils.command.SubsystemCommand;

public class StopRearPicker extends SubsystemCommand<RearPicker> {

    public StopRearPicker() {
        super(RearPicker.getInstance());
    }

    @Override
    protected void execute() {
        system.hold();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}

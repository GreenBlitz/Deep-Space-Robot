/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ExtendRoller extends SubsystemCommand<Roller> {
    public ExtendRoller() {
        super(Roller.getInstance());
    }

    @Override
    protected void execute() {
        system.extend();
    }

    @Override
    protected boolean isFinished() {
        return system.isExtended();
    }
}
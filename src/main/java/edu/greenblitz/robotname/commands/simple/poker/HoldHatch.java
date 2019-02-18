/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class HoldHatch extends SubsystemCommand<FrontPoker> {

    public HoldHatch() {
        super(FrontPoker.getInstance());
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

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.greenblitz.robotname.subsystems.Poker;

public class TogglePokerHolder extends SubsystemCommand<Poker> {

    public TogglePokerHolder() {
        super(Poker.getInstance());
    }

    @Override
    protected void execute() {
        system.hold(!system.isHeld());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

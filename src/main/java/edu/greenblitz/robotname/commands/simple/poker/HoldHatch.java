/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class HoldHatch extends SubsystemCommand<Poker> {

    public HoldHatch() {
        super(Poker.getInstance());
    }

    @Override
    protected void execute() {
        system.hold(true);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

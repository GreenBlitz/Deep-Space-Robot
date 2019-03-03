/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class BrakeElevator extends SubsystemCommand<Elevator> {

    public BrakeElevator() {
        super(Elevator.getInstance());
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void initialize() {
        system.brake(true);
        system.setRawPower(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void atEnd() {
        system.brake(false);
    }
}
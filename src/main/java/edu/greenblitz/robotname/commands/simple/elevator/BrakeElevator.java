/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;

public class BrakeElevator extends SubsystemCommand<Elevator> {

    private static final double POWER = 0.05;

    public BrakeElevator() {
        super(Elevator.getInstance());
    }

    @Override
    protected void initialize() {
        system.brake(true);
    }

    @Override
    protected void execute() {
        system.setRawPower(POWER);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        super.end();
        system.brake(false);
    }
}

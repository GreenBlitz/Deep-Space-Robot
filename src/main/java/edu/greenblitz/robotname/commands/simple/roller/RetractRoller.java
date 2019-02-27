/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class RetractRoller extends TimedSubsystemCommand<Roller> {
    private static final long ROLLER_RETRACTION_TIMEOUT = 1000;

    public RetractRoller() {
        super(Roller.getInstance(), ROLLER_RETRACTION_TIMEOUT);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Kicker.getInstance(), Elevator.getInstance()};
    }

    @Override
    protected void timedInitialize() {
        system.retract();
    }
}

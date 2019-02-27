/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MoveElevatorByMagic extends AbstractNativeElevatorMove {
    public MoveElevatorByMagic(double destination, int loopIdx, long timeOnTarget) {
        super(destination, loopIdx, timeOnTarget);
    }

    @Override
    void startNativeMove() {
        system.setSmartPosition(m_destination);
    }
}
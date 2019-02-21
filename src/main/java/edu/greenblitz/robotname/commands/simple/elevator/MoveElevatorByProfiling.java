/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.elevator;

import edu.wpi.first.wpilibj.command.Command;

// TODO: motionify
public class MoveElevatorByProfiling extends Command {
    private double m_height;

    public MoveElevatorByProfiling(double height) {
        m_height = height;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

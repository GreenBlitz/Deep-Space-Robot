/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Elevator.Level;
import edu.greenblitz.utils.command.SubsystemCommand;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class MoveElevatorByExternalPID extends SubsystemCommand<Elevator> {

    private static final PIDObject PID_CONFIG = new PIDObject(0, 0, 0, 0);
    private static final ITolerance TOLERANCE = new AbsoluteTolerance(0);
    private static int m_timesOnTarget = 0;
    private PIDController m_controller;

    public MoveElevatorByExternalPID(double height) {
        super(Elevator.getInstance());
        m_controller = new PIDController(PID_CONFIG, TOLERANCE);
        m_controller.setGoal(height);
    }

    public MoveElevatorByExternalPID(Level level) {
        this(level.getHeight());
    }

    @Override
    protected void initialize() {
        m_timesOnTarget = 0;
    }

    @Override
    protected void execute() {
        if (m_controller.isFinished())
            m_timesOnTarget++;
        else
            m_timesOnTarget = 0;
    }

    @Override
    protected boolean isFinished() {
        return m_timesOnTarget >= 4;
    }

    @Override
    protected void end() {
        system.stop();
    }
}
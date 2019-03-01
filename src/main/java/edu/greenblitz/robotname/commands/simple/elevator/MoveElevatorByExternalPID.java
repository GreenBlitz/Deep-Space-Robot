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
import edu.greenblitz.robotname.data.sm.ElevatorState;
import edu.greenblitz.robotname.data.sm.State;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

import java.util.Optional;

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
        var in = system.getHeight();
        var out = m_controller.calculatePID(in);
        system.setRawPower(out);
        if (m_controller.isFinished(in))
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
        super.end();
        system.stop();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(
                new State(ElevatorState.closestTo(m_controller.getGoal()),
                        null, null, null));
    }
}
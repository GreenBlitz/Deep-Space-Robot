/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Elevator.Level;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class MoveElevatorByExternalPID extends TimedSubsystemCommand<Elevator> {

    private static final long ELEVATOR_PID_TIMEOUT = 5000;
    private static final long ELEVATOR_PID_DELAY = 5000;

    private static final PIDObject PID_CONFIG = new PIDObject(0, 0, 0, 0);
    private static final ITolerance TOLERANCE = new AbsoluteTolerance(0);
    private static int m_timesOnTarget = 0;
    private PIDController m_controller;

    public MoveElevatorByExternalPID(double height) {
        super(Elevator.getInstance(), ELEVATOR_PID_TIMEOUT, ELEVATOR_PID_DELAY);
        m_controller = new PIDController(PID_CONFIG, TOLERANCE);
        m_controller.setGoal(height);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Roller.getInstance()};
    }

    public MoveElevatorByExternalPID(Level level) {
        this(level.getHeight());
    }

    @Override
    protected void timedInitialize() {
        m_timesOnTarget = 0;
    }

    @Override
    protected void rawExecute() {
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
}
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator.Level;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class MoveElevatorByExternalPID extends SubsystemCommand<Elevator> implements PIDSource, PIDOutput {

    private static final double kP = 0, kI = 0, kD = 0, kF = 0;

    private static int m_timesOnTarget = 0;
    private PIDController m_controller;

    public MoveElevatorByExternalPID(double height) {
        super(Elevator.getInstance());
        m_controller = new PIDController(kP, kI, kD, kF, this, this);
        m_controller.setAbsoluteTolerance(0.05);
        m_controller.setSetpoint(height);
    }

    public MoveElevatorByExternalPID(Level level) {
        this(level.getHeight());
    }

    @Override
    protected void initialize() {
        m_controller.enable();
        m_timesOnTarget = 0;
    }

    @Override
    protected void execute() {
        if (m_controller.onTarget())
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
        m_controller.disable();
        system.stop();
    }

    @Override
    public void pidWrite(double output) {
        system.setRawPower(output);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return system.getHeight();
    }
}
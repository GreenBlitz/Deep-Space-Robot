/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.chassis;

import edu.greenblitz.robotname.VisionPort;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.Chassis;

public class DriveToVisionTarget extends Command implements PIDSource, PIDOutput {

    private static final double kP = 0.75, kI = 0, kD = 0, turnkP = 0.03;
    private PIDController m_controller;
    private static final long TIME_ON_TARGET = 200;
    private long m_onTarget = -1;
    private static final double MINIMUM_OUTPUT = 0.35;

    public DriveToVisionTarget() {
        requires(Chassis.getInstance());
        m_controller = new PIDController(kP, kI, kD, this, this);
    }

    @Override
    protected void initialize() {
        m_onTarget = -1;
        m_controller.setAbsoluteTolerance(0.1);
        m_controller.setSetpoint(0.5);
        m_controller.setOutputRange(-0.8, 0.8);
        m_controller.enable();
    }

    @Override
    protected void execute() {
        if (m_controller.onTarget())
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
            else
                m_onTarget = -1;
    }

    @Override
    protected boolean isFinished() {
        //return m_controller.onTarget() && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
        return false;
    }

    @Override
    protected void end() {
        m_controller.disable();
        Chassis.getInstance().stop();
    }

    @Override
    public void pidWrite(double output) {
        if (Math.abs(output) < MINIMUM_OUTPUT)
            output = Math.signum(output) * MINIMUM_OUTPUT;
        SmartDashboard.putNumber("PID Output", output);
        Chassis.getInstance().arcadeDrive(-output, -turnkP*(VisionPort.getInstance().getHatchAngle()));
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {}

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return VisionPort.getInstance().getHatchDistance();
    }
}
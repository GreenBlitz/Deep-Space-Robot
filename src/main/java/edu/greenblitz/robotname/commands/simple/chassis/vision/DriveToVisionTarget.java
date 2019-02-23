/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import org.greenblitz.motion.pid.MultivariablePIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;

public class DriveToVisionTarget extends Command {

    private static final int DRIVE_IDX = 0;
    private static final int TURN_IDX = 1;

    private static final PIDObject DRIVE = new PIDObject(0, 0, 0);
    private static final PIDObject TURN = new PIDObject(0, 0, 0);

    private MultivariablePIDController m_controller;

    private static final long TIME_ON_TARGET = 200;
    private long m_onTarget = -1;

    public DriveToVisionTarget() {
        requires(Chassis.getInstance());
        m_controller = new MultivariablePIDController(2);
        m_controller.configure(DRIVE_IDX, DRIVE, new AbsoluteTolerance(0.1));
        m_controller.configure(TURN_IDX, TURN, new AbsoluteTolerance(3));
    }

    @Override
    protected void initialize() {
        m_onTarget = -1;
        m_controller.setGoals(0, 0);
    }

    @Override
    protected void execute() {
        var state = VisionMaster.getInstance().getStandardizedData();
        var inputDrive = Math.hypot(state.x, state.y);
        var inputTurn = VisionMaster.getInstance().getStandardizedData().getCenterAngle();
        var pidResult = m_controller.calculate(inputDrive, inputTurn);

        Chassis.getInstance().arcadeDrive(pidResult[0], pidResult[1]);

        if (m_controller.isFinished())
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
            else
                m_onTarget = -1;

    }

    @Override
    protected boolean isFinished() {
        return m_controller.isFinished() && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
    }

    @Override
    protected void end() {
        Chassis.getInstance().stop();
    }
}
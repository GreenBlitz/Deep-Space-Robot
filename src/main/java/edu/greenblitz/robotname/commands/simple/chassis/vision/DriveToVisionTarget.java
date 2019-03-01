/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.pid.MultivariablePIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

import java.util.Optional;

public class DriveToVisionTarget extends ChassisBaseCommand {

    private static final int DRIVE_IDX = 0;
    private static final int TURN_IDX = 1;

    private static final PIDObject DRIVE = new PIDObject(0, 0, 0);
    private static final PIDObject TURN = new PIDObject(0, 0, 0);

    private static final ITolerance DRIVE_TOL = new AbsoluteTolerance(0.1);
    private static final ITolerance TURN_TOL = new AbsoluteTolerance(3);

    private MultivariablePIDController m_controller;

    private static final long TIME_ON_TARGET = 200;
    private long m_onTarget = -1;

    public DriveToVisionTarget() {
        m_controller = new MultivariablePIDController(2);
        m_controller.setPIDObject(DRIVE_IDX, DRIVE, DRIVE_TOL);
        m_controller.setPIDObject(TURN_IDX, TURN, TURN_TOL);
    }

    @Override
    protected void initialize() {
        m_onTarget = -1;
        m_controller.setGoals(0, 0);
    }

    @Override
    protected void execute() {
        var state = VisionMaster.getInstance().getStandardizedData();
        var inputDrive = state.getPlaneryDistance();
        var inputTurn = VisionMaster.getInstance().getStandardizedData().getCenterAngle();
        var pidResult = m_controller.calculate(inputDrive, inputTurn);

        Chassis.getInstance().arcadeDrive(pidResult[DRIVE_IDX], pidResult[TURN_IDX]);

        if (m_controller.isFinished(new double[]{inputDrive, inputTurn}))
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
            else
                m_onTarget = -1;
    }

    @Override
    protected boolean isFinished() {
        var state = VisionMaster.getInstance().getStandardizedData();
        var inputDrive = state.getPlaneryDistance();
        var inputTurn = VisionMaster.getInstance().getStandardizedData().getCenterAngle();
        var arr = new double[]{inputDrive, inputTurn};
        return m_controller.isFinished(arr) && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
    }

    @Override
    protected void end() {
        super.end();
        Chassis.getInstance().stop();
    }
}
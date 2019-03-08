package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import org.greenblitz.motion.pid.MultivariablePIDController;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.tolerance.AbsoluteTolerance;
import org.greenblitz.motion.tolerance.ITolerance;

public class DriveStraightByDistance extends ChassisBaseCommand {

    private static final int DRIVE_IDX = 0;
    private static final int TURN_IDX = 1;

    private static final PIDObject DRIVE = new PIDObject(0.75, 0, 0);
    private static final PIDObject TURN = new PIDObject(0.02, 0, 0);

    private static final ITolerance DRIVE_TOL = new AbsoluteTolerance(0.1);
    private static final ITolerance TURN_TOL = new AbsoluteTolerance(3);

    private static final double POWER_LIMIT = 0.4;

    private MultivariablePIDController m_controller;

    private static final long TIME_ON_TARGET = 1000;
    private long m_onTarget = -1;

    private double m_distance;

    public DriveStraightByDistance(double distance, long ms) {
        super(ms);
        m_distance = distance;

        m_controller = new MultivariablePIDController(2);
        m_controller.setPIDObject(DRIVE_IDX, DRIVE, DRIVE_TOL);
        m_controller.setPIDObject(TURN_IDX, TURN, TURN_TOL);
    }

    public DriveStraightByDistance(double distance) {
        m_distance = distance;

        m_controller = new MultivariablePIDController(2);
        m_controller.setPIDObject(DRIVE_IDX, DRIVE, DRIVE_TOL);
        m_controller.setPIDObject(TURN_IDX, TURN, TURN_TOL);    }

    @Override
    protected void initialize() {
        var inputDrive = Chassis.getInstance().getDistance();
        var inputTurn = Chassis.getInstance().getAngle();

        m_onTarget = -1;

        m_controller.configurePID(DRIVE_IDX, inputDrive, inputDrive + m_distance, -POWER_LIMIT, POWER_LIMIT, 0);
        m_controller.configurePID(TURN_IDX, inputTurn, inputTurn, -POWER_LIMIT, POWER_LIMIT, 0);
    }

    @Override
    protected void execute() {
        var inputDrive = Chassis.getInstance().getDistance();
        var inputTurn = Chassis.getInstance().getAngle();
        var pidResult = m_controller.calculate(inputDrive, inputTurn);

        Chassis.getInstance().arcadeDrive(-pidResult[DRIVE_IDX], -pidResult[TURN_IDX]);

        if (m_controller.isFinished(new double[]{inputDrive, inputTurn})) {
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
        } else {
            m_onTarget = -1;
        }
    }

    @Override
    protected boolean isFinished() {
        var inputDrive = Chassis.getInstance().getDistance();
        var inputTurn = Chassis.getInstance().getAngle();
        boolean ret1 = m_controller.isFinished(new double[]{inputDrive, inputTurn});
        boolean ret2 = System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
        return ret1 && ret2;
    }

    @Override
    protected void atEnd() {
        Chassis.getInstance().stop();
    }
}
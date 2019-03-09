package edu.greenblitz.robotname;

import edu.greenblitz.robotname.data.Report;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.*;
import edu.greenblitz.utils.sm.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class Robot extends TimedRobot {

    private static class RobotSupplier implements Supplier<Robot> {
        private Robot currentRobot;

        @Override
        public Robot get() {
            currentRobot = new Robot();
            return currentRobot;
        }
    }

    private static RobotSupplier robotFactory = new RobotSupplier();

    public static void main(String... args) {
        RobotBase.startRobot(robotFactory);
    }

    public static Robot getInstance() {
        return robotFactory.currentRobot;
    }

    private PowerDistributionPanel m_pdp;
    private Logger logger;
    private StateMachine m_status;
    private Report m_usageReport;

    /**
     * @deprecated state machine updates were commented out due to unclear bugs, so every part of the sm shouldn't be used
     * @return current robot's state machine
     */
    @Deprecated
    public StateMachine getStateMachine() {
        return m_status;
    }

    /**
     * @deprecated state machine updates were commented out due to unclear bugs, so every part of the sm shouldn't be used
     * @return current robot state
     */
    @Deprecated
    public State getCurrentState() {
        return m_status.getCurrentState();
    }

    @Override
    public void robotInit() {
        logger = LogManager.getLogger(getClass());
        m_usageReport = new Report();
        m_status = StateMachineGenerator.createMachine(
                new State(ElevatorState.GROUND, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.UNKICK)
        );

        OI.initJoysticks();

        SmartDashboard.putData(Scheduler.getInstance());
        allowChassis();
        allowElevator();
        allowKicker();
        allowPoker();
        allowPneumatics();
        allowRoller();
        allowClimber();
        allowShifter();

        OI.initBindings();
        m_pdp = new PowerDistributionPanel();

        Stream.init();
        VisionMaster.init();

        var pi = new Thread(new Pi());
        pi.start();
    }

    @Override
    public void disabledInit() {
        reset();
        Scheduler.getInstance().removeAll();
        if (m_usageReport.isReportValid()) report();
    }

    private void report() {
        m_usageReport.toShuffleboard();

        System.out.println("-----------------------------------------------------");
        System.out.println(m_usageReport.getTotalReport());
        System.out.println("-----------------------------------------------------");
    }

    private void matchInit() {
        Scheduler.getInstance().removeAll();
        reset();
        m_usageReport.setVoltageAtInit(m_pdp.getVoltage());
    }

    @Override
    public void autonomousInit() {
        matchInit();
    }

    @Override
    public void teleopInit() {
        if (DriverStation.getInstance().isFMSAttached()) {
            logger.info("WERE IN FOR A REAL MATCH BOYS!");
            // This is for a real match
            Scheduler.getInstance().removeAll();
        } else {
            logger.info("testing...");
            // This is for testing
            matchInit();
        }
    }

    @Override
    public void robotPeriodic() {
        update();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    private void update() {
        Elevator.getInstance().update();
        Chassis.getInstance().update();
        VisionMaster.getInstance().update();
        OI.update();
    }

    private void reset() {
        Chassis.getInstance().reset();
        Elevator.getInstance().reset();
        Poker.getInstance().reset();
        Roller.getInstance().reset();
        Kicker.getInstance().reset();
        Shifter.getInstance().reset();
        Pneumatics.getInstance().reset();

        m_usageReport.reset();
        m_status.setCurrentState(
                new State(ElevatorState.GROUND, RollerState.RETRACTED, PokerState.UNPOKING, KickerState.UNKICK)
        );
    }


    public Report getReport() {
        return m_usageReport;
    }

    private void allowChassis() {
        Chassis.init();
        SmartDashboard.putData(Chassis.getInstance());
    }

    private void allowShifter() {
        Shifter.init();
        SmartDashboard.putData(Shifter.getInstance());
    }

    private void allowElevator() {
        Elevator.init();
        SmartDashboard.putData(Elevator.getInstance());
    }

    private void allowRoller() {
        Roller.init();
        SmartDashboard.putData(Roller.getInstance());
    }

    private void allowKicker() {
        Kicker.init();
        SmartDashboard.putData(Kicker.getInstance());
    }

    private void allowPoker() {
        Poker.init();
        SmartDashboard.putData(Poker.getInstance());
    }

    private void allowClimber() {
        Climber.init();
        SmartDashboard.putData(Climber.getInstance().getBig());
        SmartDashboard.putData(Climber.getInstance().getExtender());
        SmartDashboard.putData(Climber.getInstance().getWheels());
    }

    private void allowPneumatics() {
        Pneumatics.init();
        SmartDashboard.putData(Pneumatics.getInstance());
    }
}
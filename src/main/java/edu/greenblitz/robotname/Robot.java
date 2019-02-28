package edu.greenblitz.robotname;

import edu.greenblitz.robotname.data.GeneralState;
import edu.greenblitz.robotname.data.Report;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.*;
import edu.greenblitz.utils.sendables.SendableDoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
    private GeneralState m_state;
    private Logger logger;
    private Report m_usageReport;

    @Override
    public void robotInit() {
        logger = LogManager.getLogger(getClass());
        m_usageReport = new Report();

        Chassis.init();
//        Shifter.initChain();
//        Climber.initChain();
//        Elevator.init();
//        Roller.init();
//        Kicker.init();
//        Poker.init();
//        Pneumatics.init();


//        m_state = new GeneralState();
        m_pdp = new PowerDistributionPanel();

        OI.init();

        SmartDashboard.putData(Scheduler.getInstance());

        SmartDashboard.putData(Chassis.getInstance());
//        SmartDashboard.putData(Shifter.getInstance());
//        SmartDashboard.putData(Climber.getInstance().getBig());
//        SmartDashboard.putData(Climber.getInstance().getWheels());
//        SmartDashboard.putData(Climber.getInstance().getExtender());
//        SmartDashboard.putData(Elevator.getInstance());
//        SmartDashboard.putData(Roller.getInstance());
//        SmartDashboard.putData(Kicker.getInstance());
//        SmartDashboard.putData(Poker.getInstance());
//        SmartDashboard.putData(Pneumatics.getInstance());

        VisionMaster.init();
        // Chassis.getInstance().startLoclizer();
    }

    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
        m_usageReport.toShuffleboard();

        System.out.println("-----------------------------------------------------");
        System.out.println(m_usageReport.getTotalReport());
        System.out.println("-----------------------------------------------------");
    }

    private void matchInit() {
        // Chassis.getInstance().reset();
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
//        Elevator.getInstance().update();
//        m_state.update();
        OI.update();
    }

    private void reset() {
//        Chassis.getInstance().reset();
//        Elevator.getInstance().reset();

        m_usageReport.reset();
//        m_state.reset();
    }

    public GeneralState getState() {
        return m_state;
    }

    public Report getReport() {
        return m_usageReport;
    }
}

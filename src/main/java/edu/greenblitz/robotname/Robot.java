package edu.greenblitz.robotname;

import edu.greenblitz.robotname.data.GeneralState;
import edu.greenblitz.robotname.data.Report;
import edu.greenblitz.robotname.data.vision.StandardVisionData;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.*;
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
    private GeneralState m_state;
    private Logger logger;

    @Override
    public void robotInit() {
        logger = LogManager.getLogger(getClass());
//        Chassis.init();
//        Shifter.init();
//        Climber.init();
//        Elevator.init();
        Roller.init();
        Kicker.init();
        FrontPoker.init();
        Pneumatics.init();

//        m_state = new GeneralState();
        m_pdp = new PowerDistributionPanel();
        Report.init();

        OI.init();

        SmartDashboard.putData(Scheduler.getInstance());
//        SmartDashboard.putData(Chassis.getInstance());
//        SmartDashboard.putData(Shifter.getInstance());
//        SmartDashboard.putData(Climber.getInstance().getBig());
//        SmartDashboard.putData(Climber.getInstance().getWheels());
//        SmartDashboard.putData(Climber.getInstance().getExtender());
//        SmartDashboard.putData(Elevator.getInstance());
        SmartDashboard.putData(Roller.getInstance());
        SmartDashboard.putData(Kicker.getInstance());
        SmartDashboard.putData(FrontPoker.getInstance());
        SmartDashboard.putData(Pneumatics.getInstance());
        VisionMaster.init();
        // Chassis.getInstance().startLoclizer();
    }

    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
        Report.toShuffleboard();

        System.out.println("-----------------------------------------------------");
        System.out.println(Report.getTotalReport());
        System.out.println("-----------------------------------------------------");
    }

    private void matchInit() {
        // Chassis.getInstance().reset();
        Scheduler.getInstance().removeAll();
        reset();
        Report.voltageAtInit(m_pdp.getVoltage());
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
        OI.update();
//        Elevator.getInstance().update();
//        m_state.update();
    }

    private void reset() {
        // Chassis.getInstance().reset();
//        Elevator.getInstance().reset();

        Report.reset();
//        m_state.reset();
    }

    public GeneralState getState() {
        return m_state;
    }
}

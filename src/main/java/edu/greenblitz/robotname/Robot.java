package edu.greenblitz.robotname;

import edu.greenblitz.robotname.data.GeneralState;
import edu.greenblitz.robotname.data.Report;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
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
        Chassis.init();
        Chassis.getInstance().startLoclizer();
        Shifter.init();
//        Climber.init();
//        Elevator.init();
//        Roller.init();
//        Kicker.init();
//        FrontPoker.init();
        Pneumatics.init();

//        m_state = new GeneralState();
        m_pdp = new PowerDistributionPanel();
        Report.init();

        OI.init();
    }

    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
        Shifter.getInstance().setShift(Shifter.Gear.POWER);
        Report.toShuffleboard();

        System.out.println("-----------------------------------------------------");
        System.out.println(Report.getTotalReport());
        System.out.println("-----------------------------------------------------");
    }

    private void matchInit() {
        Chassis.getInstance().reset();
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
            // TODO change to Power later
            Shifter.getInstance().setShift(Shifter.Gear.SPEED);
        }
    }

    @Override
    public void robotPeriodic() {
        updateSubsystems();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    private void updateSubsystems() {
        Chassis.getInstance().update();
        Shifter.getInstance().update();
//        Climber.getInstance().update();
//        Elevator.getInstance().update();
//        Roller.getInstance().update();
//        Kicker.getInstance().update();
//        FrontPoker.getInstance().update();
        Pneumatics.getInstance().update();

        OI.update();
//        m_state.update();
    }

    private void reset() {
        Chassis.getInstance().reset();
//        Elevator.getInstance().reset();

        Report.reset();
//        m_state.reset();
    }

    public GeneralState getState() {
        return m_state;
    }
}

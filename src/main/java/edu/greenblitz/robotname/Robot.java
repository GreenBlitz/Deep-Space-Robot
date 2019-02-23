package edu.greenblitz.robotname;

import edu.greenblitz.robotname.data.GeneralState;
import edu.greenblitz.robotname.data.Report;
import edu.greenblitz.utils.Logging;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.greenblitz.robotname.subsystems.*;

import java.util.function.Supplier;
import java.util.logging.Logger;

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

    @Override
    public void robotInit() {
        Logging.init();
        Logger.getLogger("chassis").fine("fine");
        Logger.getLogger("chassis").info("info");
        System.out.println("shit logged");
        //Chassis.init();
        //Shifter.init();
        //Climber.init();
        //Elevator.init();
        //Roller.init();
        //Kicker.init();
        //FrontPoker.init();
        //Pneumatics.init();

        //m_state = new GeneralState();
        //m_pdp = new PowerDistributionPanel();
        //Report.init();

        //OI.init();
    }
    
    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
        //Report.toShuffleboard();

        System.out.println("-----------------------------------------------------");
        //System.out.println(Report.getTotalReport());
        System.out.println("-----------------------------------------------------");
    }

    private void matchInit() {
        Scheduler.getInstance().removeAll();
        reset();
        //Report.voltageAtInit(m_pdp.getVoltage());
    }

    @Override
    public void autonomousInit() {
        matchInit();
    }

    @Override
    public void teleopInit() {
        if (DriverStation.getInstance().isFMSAttached()) {
            // This is for a real match
            Scheduler.getInstance().removeAll();
        } else {
            // This is for practicing
            matchInit();
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
        //Chassis.getInstance().update();
        //Shifter.getInstance().update();
        //Climber.getInstance().update();
        //Elevator.getInstance().update();
        //Roller.getInstance().update();
        //Kicker.getInstance().update();
        //FrontPoker.getInstance().update();
        //Pneumatics.getInstance().update();

        //OI.update();
        //m_state.update();
    }

    private void reset() {
        //Chassis.getInstance().reset();
        //Elevator.getInstance().reset();

        //Report.reset();
        //m_state.reset();
    }

    public GeneralState getState() {
        return m_state;
    }
}

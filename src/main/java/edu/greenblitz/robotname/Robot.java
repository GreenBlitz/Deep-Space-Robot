package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.chassis.autonomous.AutoFallAndThreeHalfs;
import edu.greenblitz.robotname.commands.complex.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.robotname.commands.simple.shifter.KeepShift;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.robotname.data.Report;
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
import org.greenblitz.debug.RemoteGuydeBugger;
import org.greenblitz.motion.app.Localizer;

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
    private Report m_usageReport;

    @Override
    public void robotInit() {
        logger = LogManager.getLogger(getClass());
        m_usageReport = new Report();

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

//        Stream.init();
        Pi.init();
        VisionMaster.init();
        Paths.init("Cargoship1", "Cargoship2", "Cargoship3", "Cargoship4", "FallAndPlace");

        SmartDashboard.putData("Elevator GR", new SafeMoveElevator(Elevator.Level.GROUND));
        SmartDashboard.putData("Elevator R1", new SafeMoveElevator(Elevator.Level.ROCKET_LOW));
        SmartDashboard.putData("Elevator R2", new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        SmartDashboard.putData("Elevator R3", new SafeMoveElevator(Elevator.Level.ROCKET_HIGH));
        SmartDashboard.putData("Elevator CS", new SafeMoveElevator(Elevator.Level.CARGO_SHIP));
    }

    @Override
    public void disabledInit() {
        reset();
        Scheduler.getInstance().removeAll();
        Chassis.getInstance().stop();
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
        VisionMaster.getInstance().setCurrentFocus(VisionMaster.Focus.RIGHT);
    }

    @Override
    public void autonomousInit() {
        matchInit();
        new KeepShift().start();

        new AutoFallAndThreeHalfs(true).start();
    }

    @Override
    public void teleopInit() {
        if (DriverStation.getInstance().isFMSAttached()) {
            logger.info("WERE IN FOR A REAL MATCH BOYS!");
            // This is for a real match
            Scheduler.getInstance().removeAll();
            new AutoChangeShift().start();
        } else {
            logger.info("testing...");
            // This is for testing
            matchInit();
            new OI.ToHatchMode().start();
            new ToPower().start();
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
        var loc = Localizer.getInstance().getLocation();
        RemoteGuydeBugger.report(-loc.getX(), -loc.getY(), loc.getAngle());

    }

    private void update() {
        Pi.update();
        OI.update();
        VisionMaster.getInstance().update();
    }

    private void reset() {
        Chassis.getInstance().reset();
        Elevator.getInstance().reset();
        Poker.getInstance().reset();
        Roller.getInstance().reset();
        Kicker.getInstance().reset();
        Shifter.getInstance().reset();
        Pneumatics.getInstance().reset();
        Climber.getInstance().getExtender().reset();
        Scheduler.getInstance().removeAll();

        m_usageReport.reset();
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
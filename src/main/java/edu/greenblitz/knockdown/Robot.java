package edu.greenblitz.knockdown;

import edu.greenblitz.knockdown.commands.complex.chassis.autonomous.AutoFallAndThreeHalfs;
import edu.greenblitz.knockdown.commands.complex.chassis.autonomous.AutoThreeHalfFarRocket;
import edu.greenblitz.knockdown.commands.complex.elevator.SafeMoveElevator;
import edu.greenblitz.knockdown.commands.simple.chassis.FallWithNavx;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.knockdown.commands.simple.shifter.KeepShift;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.knockdown.commands.simple.shifter.ToSpeed;
import edu.greenblitz.knockdown.data.Report;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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

    public enum Autonomii{
        NOTHING,
        HAB_2_FALL,
        ROCKET_FAR,
        CARGOSHIP
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

    private SendableChooser<Autonomii> autoChooser;
    private SendableChooser<Boolean> sideChooser;
    private SendableChooser<Command> shiftChooser;
    private SendableChooser<Boolean> hab2Chooser;

    @Override
    public void robotInit() {
        logger = LogManager.getLogger(getClass());
        m_usageReport = new Report();
//        SmartDashboard.putNumber("Auto x offset (positive = left)", 0);
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

        Pi.init();
        VisionMaster.init();
//        Stream.init();

        autoChooser = new SendableChooser<>();
        sideChooser = new SendableChooser<>();
        hab2Chooser = new SendableChooser<>();
        shiftChooser = new SendableChooser<>();

        shiftChooser.setDefaultOption("Speed", new ToSpeed());
        shiftChooser.addOption("Power", new ToPower());

        sideChooser.setDefaultOption("Left", true);
        sideChooser.addOption("Right", false);

        hab2Chooser.setDefaultOption("Hab 2", true);
        hab2Chooser.addOption("Hab 1", false);

        autoChooser.setDefaultOption("Do nothing", Autonomii.NOTHING);
        autoChooser.addOption("Cargoship and Collect", Autonomii.CARGOSHIP);
        autoChooser.addOption("Rocket far and Collect", Autonomii.ROCKET_FAR);
        autoChooser.addOption("Hab 2 fall", Autonomii.HAB_2_FALL);

        SmartDashboard.putData("Side Chooser", sideChooser);
        SmartDashboard.putData("Hab2 Chooser", hab2Chooser);
        SmartDashboard.putData("Auto Chooser", autoChooser);
        SmartDashboard.putData("Shift Chooser", shiftChooser);

        SmartDashboard.putData("Elevator GR", new SafeMoveElevator(Elevator.Level.GROUND));
        SmartDashboard.putData("Elevator R1", new SafeMoveElevator(Elevator.Level.ROCKET_LOW));
        SmartDashboard.putData("Elevator R2", new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        SmartDashboard.putData("Elevator R3", new SafeMoveElevator(Elevator.Level.ROCKET_HIGH));
        SmartDashboard.putData("Elevator CS", new SafeMoveElevator(Elevator.Level.CARGO_SHIP));
    }

    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();

        if (DriverStation.getInstance().isFMSAttached()) {
            return;
        }

        reset();
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
        logger.debug(DriverStation.getInstance().getMatchType());
    }

    @Override
    public void autonomousInit() {
        matchInit();
        new KeepShift().start();
        Autonomii autonomousName = autoChooser.getSelected();
        Command autonomous = new ArcadeDriveByJoystick(OI.getMainJoystick());
        switch (autonomousName){
            case NOTHING:
                break;
            case HAB_2_FALL:
                autonomous = new FallWithNavx();
                break;
            case CARGOSHIP:
                autonomous = new AutoFallAndThreeHalfs(sideChooser.getSelected(), hab2Chooser.getSelected());
                break;
            case ROCKET_FAR:
                autonomous = new AutoThreeHalfFarRocket(sideChooser.getSelected(), hab2Chooser.getSelected());
                break;
            default:
                logger.error("No logical autonomous selected, running arcade drive.");
                break;
        }
        logger.debug("{} was selected for the autonomous!", autonomous.getName());
        autonomous.start();
    }

    @Override
    public void teleopInit() {
        if (DriverStation.getInstance().isFMSAttached()) {
            logger.info("WERE IN FOR A REAL MATCH BOYS!");
            // This is for a real match
            Scheduler.getInstance().removeAll();
            Shifter.getInstance().setDefaultCommand(new KeepShift());
            shiftChooser.getSelected().start();
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
//        Poker.getInstance().reset();
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

    private boolean shouldReset() {
        return DriverStation.getInstance().isFMSAttached();
    }

    public Command getChosenGearCommand() {
        return shiftChooser.getSelected();
    }
}
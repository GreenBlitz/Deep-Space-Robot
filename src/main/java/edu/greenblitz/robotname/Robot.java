package edu.greenblitz.robotname;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.greenblitz.robotname.subsystems.*;

public class Robot extends TimedRobot {

    /*
        TODO: Add algorithm check with vision
        TODO: Add automator for roller & elevator
        TODO: Add command groups
     */

    private PowerDistributionPanel m_pdp;

    @Override
    public void robotInit() {
        OI.init();

        Chassis.init();
        Shifter.init();
        Climber.init();
        Elevator.init();
        Roller.init();
        Kicker.init();
        RearPicker.init();
        FrontPoker.init();
        Pneumatics.init();

        m_pdp = new PowerDistributionPanel();
    }
    
    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
        Report.toShuffleboard();
    }

    private void enabledInit() {
        Scheduler.getInstance().removeAll();
        Report.reset();
        Report.voltageAtInit(m_pdp.getVoltage());
    }

    @Override
    public void autonomousInit() {
        enabledInit();
    }

    @Override
    public void teleopInit() {
        enabledInit();
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
        OI.update();

        Chassis.getInstance().update();
        Shifter.getInstance().update();
        Climber.getInstance().update();
        Elevator.getInstance().update();
        Roller.getInstance().update();
        Kicker.getInstance().update();
        RearPicker.getInstance().update();
        FrontPoker.getInstance().update();
        Pneumatics.getInstance().update();
    }
}
package edu.greenblitz.robotname;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.subsystems.*;

public class Robot extends TimedRobot {

    /*
        TODO: Add algorithm check with vision
        TODO: Add automator for roller & elevator
        TODO: Add command groups
     */

    private PowerDistributionPanel m_PDP; 
    private static double m_startingVoltage;

    @Override
    public void robotInit() {
        Chassis.init();
        Climber.init();
        Elevator.init();
        Roller.init();
        Kicker.init();
        RearPicker.init();
        FrontPoker.init();
        Pneumatics.init();
        OI.init();
        m_startingVoltage = m_PDP.getVoltage();
    }
    
    @Override
    public void disabledInit() {
        SmartDashboard.putNumber("Robot::VoltageDifference", m_startingVoltage-m_PDP.getVoltage());
        Scheduler.getInstance().removeAll();
    }

    @Override
    public void robotPeriodic() {
        updateSubsystems();
        SmartDashboard.putNumber("Robot::SolenoidChanges", Shifter.getInstance().getPistonChanges() +
                                                            Roller.getInstance().getPistonChanges() +
                                                            RearPicker.getInstance().getPistonChanges() +
                                                            Kicker.getInstance().getPistonChanges() +
                                                            FrontPoker.getInstance().getTotalPistonChanges() +
                                                            Elevator.getInstance().getPistonChanges());
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
        Climber.getInstance().update();
        Elevator.getInstance().update();
        Roller.getInstance().update();
        Kicker.getInstance().update();
        RearPicker.getInstance().update();
        FrontPoker.getInstance().update();
        Pneumatics.getInstance().update();
        OI.getInstance().update();
    }
}
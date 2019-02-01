package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
        Chassis.init();
        Climber.init();
        Elevator.init();
        Roller.init();
        Kicker.init();
        RearPicker.init();
        FrontPoker.init();
        OI.init();
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        updateSubsystems();
    }

    private void updateSubsystems() {
        Chassis.getInstance().update();
        Climber.getInstance().update();
        Elevator.getInstance().update();
        Roller.getInstance().update();
        Kicker.getInstance().update();
        RearPicker.getInstance().update();
        FrontPoker.getInstance().update();
        OI.getInstance().update();
    }
}
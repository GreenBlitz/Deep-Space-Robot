package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
        Chassis.init();
        Climber.init();
        Elevator.init();
        Intake.init();
        Kicker.init();
        Plate.init();
        Spike.init();
        OI.init();
    }

    @Override
    public void robotPeriodic() {
        updateSubsystems();
    }

    private void updateSubsystems() {
        Chassis.getInstance().update();
        Climber.getInstance().update();
        Elevator.getInstance().update();
        Intake.getInstance().update();
        Kicker.getInstance().update();
        Plate.getInstance().update();
        Spike.getInstance().update();
        OI.getInstance().update();
    }
}
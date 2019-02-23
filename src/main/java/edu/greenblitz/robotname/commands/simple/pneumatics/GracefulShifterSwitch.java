package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.wpi.first.wpilibj.command.Command;

public class GracefulShifterSwitch extends Command {
    private Shifter.Gear destination;
    private long chassisSleepTime;
    private long initTime;

    public GracefulShifterSwitch(Shifter.Gear shiftTo, long sleepTime) {
        requires(Shifter.getInstance());
        requires(Chassis.getInstance());
        destination = shiftTo;
        chassisSleepTime = sleepTime;
    }

    @Override
    protected void initialize() {
        Chassis.getInstance().stop();
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        var now = System.currentTimeMillis();
        if (now > initTime + chassisSleepTime / 2) Shifter.getInstance().setShift(destination);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > initTime + chassisSleepTime;
    }
}

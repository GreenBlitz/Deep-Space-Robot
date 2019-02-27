package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Command;

public class GracefulShifterSwitch extends GBCommand {
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

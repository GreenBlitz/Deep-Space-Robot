package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;

@Deprecated
public class GracefulShifterSwitch extends ShifterBaseCommand {
    private Shifter.Gear destination;
    private long chassisSleepTime;
    private long initTime;

    private static final long DEFAULT_DELAY = 150;

    public GracefulShifterSwitch(Shifter.Gear shiftTo, long sleepTime) {
        requires(Shifter.getInstance());
        requires(Chassis.getInstance());
        destination = shiftTo;
        chassisSleepTime = sleepTime;
    }

    public GracefulShifterSwitch(Shifter.Gear shiftTo){
        this(shiftTo, DEFAULT_DELAY);
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

package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;

public class GracefulShifterToggle extends ShifterBaseCommand {
    private long chassisSleepTime;
    private long initTime;
    private boolean didToggle;

    private static final long DEFAULT_DELAY = 50;

    public GracefulShifterToggle(long sleepTime) {
        requires(Shifter.getInstance(), Chassis.getInstance());
        chassisSleepTime = sleepTime;
    }

    public GracefulShifterToggle(){
        this(DEFAULT_DELAY);
    }

    @Override
    protected void initialize() {
        didToggle = false;
        Chassis.getInstance().toCoast();
        if (Shifter.getInstance().getCurrentGear() == Shifter.Gear.POWER)
            Chassis.getInstance().stop();
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        if (!didToggle) {
            didToggle = true;
            Shifter.getInstance().toggleShift();
        }
    }

    @Override
    protected void atEnd(){
        Chassis.getInstance().toBrake();
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > initTime + chassisSleepTime;
    }
}

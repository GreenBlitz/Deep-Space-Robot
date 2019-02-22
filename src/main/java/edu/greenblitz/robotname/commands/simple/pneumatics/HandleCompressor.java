package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.SubsystemCommand;

public class HandleCompressor extends SubsystemCommand<Pneumatics> {

    private long lastActivationTime, lastActivationDuration, lastSleepDuration;

    public HandleCompressor() {
        super(Pneumatics.getInstance());
    }

    @Override
    protected void initialize() {
        resetTiming(System.currentTimeMillis());
    }

    @Override
    protected void execute() {
        if (system.getPressure() > system.getCriticalPressureHeld()) {
            executeRestricted();
        } else {
            if (system.getPressure() < system.getMinPressureReleased() && !system.isEnabled()) {
                system.compress();
            } else if (system.getPressure() >= system.getMaxPressureReleased() && system.isEnabled()) {
                system.stop();
            }
        }
    }

    private void executeRestricted() {
        var time = System.currentTimeMillis();
        if (time > lastActivationTime + lastSleepDuration + lastActivationDuration) {
            resetTiming(time);
        }

        if (time <= lastActivationTime + lastActivationDuration) {
            if (system.isLimitOn()) {
                executeHeld();
            } else {
                executeReleased();
            }
        } else {
            system.stop();
        }
    }

    private void executeHeld() {
        system.compress();
    }

    private void executeReleased() {
        system.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    private void resetTiming(long t0) {
        var full = system.getFullDutyCycle();
        var percent = system.getDutyCiclePercent();

        var part = full * percent;

        lastActivationTime = t0;
        lastActivationDuration =  (long) part;
        lastSleepDuration = (long) (full - part);
    }
}
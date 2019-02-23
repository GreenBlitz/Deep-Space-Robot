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
        system.setCompressor(false);
        resetTiming(System.currentTimeMillis());
    }

    @Override
    protected void execute() {
        if (system.getPressure() > system.getCriticalPressureHeld()) {
            executeRestricted();
        } else {
            if (system.isGameMode()) {
                if (system.getPressure() < system.getMinPressureReleased()) {
                    system.setCompressor(true);
                } else if (system.getPressure() >= system.getMaxPressureReleased()) {
                    system.setCompressor(false);
                }
            } else {
                system.setCompressor(true);
            }
        }
    }

    private void executeRestricted() {
        var time = System.currentTimeMillis();
        if (time > lastActivationTime + lastSleepDuration + lastActivationDuration) {
            resetTiming(time);
        }

        if (time <= lastActivationTime + lastActivationDuration) {
            system.setCompressor(true);
        } else {
            system.setCompressor(false);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    private void resetTiming(long t0) {
        var full = system.getFullDutyCycle() * 1000;
        var percent = system.getDutyCyclePercent();

        var part = full * percent;

        lastActivationTime = t0;
        lastActivationDuration =  (long) part;
        lastSleepDuration = (long) (full - part);
    }
}
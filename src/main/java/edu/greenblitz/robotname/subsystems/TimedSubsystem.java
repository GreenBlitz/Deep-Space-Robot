package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TimedSubsystem extends Subsystem {

    private long endOfOperationsTime = System.currentTimeMillis();

    public long getEndOfOperationsTime() {
        return endOfOperationsTime;
    }

    public void setEndOfOperationsTime(long endOfOperationsTime) {
        this.endOfOperationsTime = endOfOperationsTime;
    }
}

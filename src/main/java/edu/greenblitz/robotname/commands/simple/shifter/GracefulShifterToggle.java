package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;

public class GracefulShifterToggle extends ShifterBaseCommand {
    private boolean didToggle;

    private static final long DEFAULT_DELAY = 0;

    public GracefulShifterToggle(long sleepTime) {
        super(sleepTime);

        requires(Chassis.getInstance());
    }

    public GracefulShifterToggle(){
        this(DEFAULT_DELAY);
    }

    @Override
    protected void atInit() {
        didToggle = false;
        Chassis.getInstance().toCoast();
        if (system.getCurrentGear() == Shifter.Gear.POWER)
            Chassis.getInstance().stop();
    }

    @Override
    protected void execute() {
        if (!didToggle) {
            didToggle = true;
            system.toggleShift();
        }
    }

    @Override
    protected void atEnd(){
        Chassis.getInstance().toBrake();
    }

    @Override
    protected boolean isFinished() {
        return didToggle && isTimedOut();
    }
}

package edu.greenblitz.robotname.commands.simple.shifter;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;

public class GracefulShifterToggle extends ShifterBaseCommand {
    private long chassisSleepTime;
    private long initTime;
    private boolean didToggle;

    private static final long DEFAULT_DELAY = 50;

    public GracefulShifterToggle(long sleepTime) {
        requires(Shifter.getInstance());
        requires(Chassis.getInstance());
        chassisSleepTime = sleepTime;
    }

    public GracefulShifterToggle(){
        this(DEFAULT_DELAY);
    }

    @Override
    protected void initialize() {
        didToggle = false;
        Chassis.getInstance().setNeutralState(CANSparkMax.IdleMode.kCoast);
        if (Shifter.getInstance().getCurrentShift() == Shifter.Gear.POWER)
            Chassis.getInstance().stop();
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        var now = System.currentTimeMillis();
        if (/*now > initTime + chassisSleepTime / 2 && */!didToggle) {
            didToggle = true;
            Shifter.getInstance().toggleShift();
        }
    }

    @Override
    protected void atEnd(){
        Chassis.getInstance().setNeutralState(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > initTime + chassisSleepTime;
    }
}

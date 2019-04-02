package edu.greenblitz.knockdown.commands.simple.shifter;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Shifter;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class AutoChangeShift extends SubsystemCommand<Shifter> {

    private static final double TO_POWER_THRESHOLD = 1.4,
            TO_SPEED_THRESHOLD = 1.6;

    private static final long TIMEOUT = 700;
    private double t0;

    public AutoChangeShift() {
        super(Shifter.getInstance());
    }

    @Override
    protected void atInit() {
        t0 = System.currentTimeMillis();
        system.setDefaultCommand(this);
    }

    @Override
    protected void execute() {
        if (Math.abs(Chassis.getInstance().getVelocity()) > TO_SPEED_THRESHOLD &&
                system.getCurrentGear() == Shifter.Gear.POWER &&
                System.currentTimeMillis() - t0 > TIMEOUT) {

            t0 = System.currentTimeMillis();
            system.setShift(Shifter.Gear.SPEED);
            Chassis.getInstance().setTickPerMeter(Shifter.Gear.SPEED);
        }

        if (Math.abs(Chassis.getInstance().getVelocity()) < TO_POWER_THRESHOLD &&
                system.getCurrentGear() == Shifter.Gear.SPEED &&
                System.currentTimeMillis() - t0 > TIMEOUT) {

            t0 = System.currentTimeMillis();
            system.setShift(Shifter.Gear.POWER);
            Chassis.getInstance().setTickPerMeter(Shifter.Gear.POWER);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

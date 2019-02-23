package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.commands.simple.pneumatics.GracefulShifterSwitch;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;

public class AutoChangeShift extends SubsystemCommand<Shifter> {

    private static final double TO_POWER_THRESHOLD = 2.4,
            TO_SPEED_THRESHOLD = 2;
    private static final long TIMEOUT = 1000;
    private double t0;

    public AutoChangeShift() {
        super(Shifter.getInstance());
    }

    @Override
    protected void initialize() {
        t0 = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        if (Math.abs(Chassis.getInstance().getVelocity()) > TO_SPEED_THRESHOLD &&
                system.getCurrentShift() == Shifter.Gear.POWER &&
                System.currentTimeMillis() - t0 > TIMEOUT) {
            t0 = System.currentTimeMillis();
            new GracefulShifterSwitch(Shifter.Gear.SPEED, 100).start();
        }

        if (Math.abs(Chassis.getInstance().getVelocity()) < TO_POWER_THRESHOLD &&
                system.getCurrentShift() == Shifter.Gear.SPEED &&
                System.currentTimeMillis() - t0 > TIMEOUT) {
            t0 = System.currentTimeMillis();
            new GracefulShifterSwitch(Shifter.Gear.POWER, 100).start();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

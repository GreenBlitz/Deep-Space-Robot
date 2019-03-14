package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class AutoChangeShift extends SubsystemCommand<Shifter> {

    private static final double TO_POWER_THRESHOLD = 1.2,
            TO_SPEED_THRESHOLD = 1.4;

    private static final long TIMEOUT = 700;
    private double t0;

    public AutoChangeShift() {
        super(Shifter.getInstance());
    }

    @Override
    protected void atInit() {
        t0 = System.currentTimeMillis();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
        if (Math.abs(Chassis.getInstance().getVelocity()) > TO_SPEED_THRESHOLD &&
                system.getCurrentGear() == Shifter.Gear.POWER &&
                System.currentTimeMillis() - t0 > TIMEOUT) {

            t0 = System.currentTimeMillis();
            system.setShift(Shifter.Gear.SPEED);
        }

        if (Math.abs(Chassis.getInstance().getVelocity()) < TO_POWER_THRESHOLD &&
                system.getCurrentGear() == Shifter.Gear.SPEED &&
                System.currentTimeMillis() - t0 > TIMEOUT) {

            t0 = System.currentTimeMillis();
            system.setShift(Shifter.Gear.POWER);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

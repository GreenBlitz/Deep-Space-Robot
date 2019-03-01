package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.app.Localizer;

public class AutoChangeShift extends SubsystemCommand<Shifter> {

    private static final double TO_POWER_THRESHOLD = 1,
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
    public State getDeltaState() {
        return new State(null, null, PokerState.UNPOKING, null);
    }

    @Override
    protected void execute() {
        if (Math.abs(Chassis.getInstance().getVelocity()) > TO_SPEED_THRESHOLD &&
                system.getCurrentShift() == Shifter.Gear.POWER &&
                System.currentTimeMillis() - t0 > TIMEOUT) {
            t0 = System.currentTimeMillis();
            Shifter.getInstance().setShift(Shifter.Gear.SPEED);
            Localizer.getInstance().setSleep(50, Chassis.getInstance().getLeftVelocity(), Chassis.getInstance().getRightVelocity());
//            new GracefulShifterSwitch(Shifter.Gear.SPEED, 100).start();
        }

        if (Math.abs(Chassis.getInstance().getVelocity()) < TO_POWER_THRESHOLD &&
                system.getCurrentShift() == Shifter.Gear.SPEED &&
                System.currentTimeMillis() - t0 > TIMEOUT) {
            t0 = System.currentTimeMillis();
            Shifter.getInstance().setShift(Shifter.Gear.POWER);
            Localizer.getInstance().setSleep(50, Chassis.getInstance().getLeftVelocity(), Chassis.getInstance().getRightVelocity());
//            new GracefulShifterSwitch(Shifter.Gear.POWER, 100).start();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

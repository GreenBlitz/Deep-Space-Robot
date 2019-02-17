package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;

public class AutoChangeShift extends SubsystemCommand<Shifter> {

    private static final int TO_POWER_THRESHOLD = 0,
            TO_SPEED_THRESHOLD = 0;

    public AutoChangeShift() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
        if (Chassis.getInstance().getSpeed() > TO_SPEED_THRESHOLD &&
                system.getCurrentShift() == Shifter.ShifterState.POWER) {
            Shifter.getInstance().setShift(Shifter.ShifterState.SPEED);
        }

        if (Chassis.getInstance().getSpeed() < TO_POWER_THRESHOLD &&
                system.getCurrentShift() == Shifter.ShifterState.SPEED) {
            system.setShift(Shifter.ShifterState.POWER);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

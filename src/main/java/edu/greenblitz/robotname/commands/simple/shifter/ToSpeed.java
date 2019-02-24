package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ToSpeed extends SubsystemCommand<Shifter> {

    public ToSpeed() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
        system.setShift(Shifter.Gear.SPEED);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

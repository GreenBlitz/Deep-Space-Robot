package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class ToPower extends SubsystemCommand<Shifter> {

    public ToPower() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
//        Localizer.getInstance().setSleep(100,
//                0, 0);
        system.setShift(Shifter.Gear.POWER);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

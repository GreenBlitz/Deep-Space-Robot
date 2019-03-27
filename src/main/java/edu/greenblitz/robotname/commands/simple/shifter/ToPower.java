package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.app.Localizer;

import java.util.Optional;

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
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

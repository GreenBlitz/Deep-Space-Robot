package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.greenblitz.utils.sm.State;
import org.greenblitz.motion.app.Localizer;

import java.util.Optional;

public class ToSpeed extends SubsystemCommand<Shifter> {

    public ToSpeed() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
        system.setShift(Shifter.Gear.SPEED);
//        Localizer.getInstance().setSleep(100,
//                0, 0);
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

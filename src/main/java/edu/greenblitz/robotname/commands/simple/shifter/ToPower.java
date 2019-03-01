package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class ToPower extends SubsystemCommand<Shifter> {

    public ToPower() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
        system.setShift(Shifter.Gear.POWER);
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, PokerState.UNPOKING, null);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

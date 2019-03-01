package edu.greenblitz.robotname.commands.complex.hidden;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.roller.RollOut;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.chain.CommandChain;

public class KickerEnsureBallOut extends CommandChain {
    private static final long TIMEOUT = 3000;

    public KickerEnsureBallOut() {
        super(TIMEOUT);
    }

    public KickerEnsureBallOut(long timeout) {
        super(timeout);
    }

    @Override
    protected void initChain() {
        if (!Elevator.getInstance().isFloorLevel()) {
            addSequential(new EnsureRollerRetracted());
            addParallel(new RollOut(), new Kick());
        } else
            addSequential(new EnsureKickerClosed());
    }
}

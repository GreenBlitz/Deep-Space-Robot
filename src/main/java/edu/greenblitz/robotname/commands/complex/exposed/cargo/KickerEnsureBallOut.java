package edu.greenblitz.robotname.commands.complex.exposed.cargo;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickAndRetract;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerExtended;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.commands.simple.roller.RollOut;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;

/**
 * End terms - A ball is kicked
 */
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
        // Locking Poker and Elevator
        addParallel(new WaitAndRequire(Poker.getInstance()), new WaitAndRequire(Elevator.getInstance()), new WaitAndRequire(Roller.getInstance()));

        if (Elevator.getInstance().isFloorLevel()) {
            // Elevator in danger zone, RUN!!!
            addSequential(new EnsureRollerExtended());
            addParallel(new RollOut(2000), new KickAndRetract());
            addSequential(new RetractRoller());
        } else {
            // Elevator too high, all clear
            addSequential(new KickAndRetract());
        }
    }
}

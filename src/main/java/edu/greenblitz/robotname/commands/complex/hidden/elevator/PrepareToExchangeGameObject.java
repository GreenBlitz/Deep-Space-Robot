package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.OI.State;
import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerExtended;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.subsystems.Elevator.Level;
import edu.greenblitz.utils.command.chain.CommandChain;

public class PrepareToExchangeGameObject extends CommandChain {

    Level level;
    State state;

    public PrepareToExchangeGameObject(State state, Level input) {
        this.state = state;
        this.level = input;
    }

    @Override
    protected void initChain() {
        addSequential(new SafeMoveElevator(level.heightByState(state)));
        addSequential(new EnsureRollerRetracted());
        if (state == State.HATCH)
            addSequential(new EnsurePokerExtended());
    }
}

package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.OI.State;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerExtended;
import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.subsystems.Elevator.Level;
import edu.greenblitz.utils.command.chain.CommandChain;

public class PrepareToExchangeGameObject extends CommandChain {

    Level level;
    State state;

    public PrepareToExchangeGameObject(State state, ExchangeHeight input){
        this.state = state;
        this.level = getLevel(state, input);
    }

    private Level getLevel(State state, ExchangeHeight input){
        switch (input){
            case ELEVATOR_RESET:
                return Level.Cargo.GROUND;
            case CARGO_SHIP:
                return state == State.CARGO ?
                        Level.Cargo.SHIP:
                        Level.Hatch.SHIP;
            case ROCKET_1:
                return state == State.CARGO ?
                        Level.Cargo.ROCKET_LOW:
                        Level.Hatch.ROCKET_LOW;
            case ROCKET_2:
                return state == State.CARGO ?
                        Level.Cargo.ROCKET_MID:
                        Level.Hatch.ROCKET_MID;
            case ROCKET_3:
                return state == State.CARGO ?
                        Level.Cargo.ROCKET_HIGH:
                        Level.Hatch.ROCKET_HIGH;
        }
        throw new IllegalArgumentException("you stupid");
    }

    @Override
    protected void initChain() {
        addSequential(new SafeMoveElevator(level));
        addSequential(new EnsureRollerRetracted());
        if(state == State.CARGO)
            addSequential(new EnsurePokerRetracted());
        else
            addSequential(new EnsurePokerExtended());
    }

    public enum ExchangeHeight{
        ELEVATOR_RESET,
        CARGO_SHIP,
        ROCKET_1,
        ROCKET_2,
        ROCKET_3
    }
}

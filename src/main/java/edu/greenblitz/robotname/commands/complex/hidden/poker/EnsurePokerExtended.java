package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.commands.simple.poker.ExtendPoker;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;

public class EnsurePokerExtended extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new WaitAndRequire(Poker.getInstance()));
        if (Poker.getInstance().isExtended()) {
            addSequential(new NullCommand());
            return;
        }
        addSequential(new DynamicCommand() {
            @Override
            protected GBCommand pick() {
                if (Kicker.getInstance().isOpen())
                    return new EnsureKickerClosed();
                return new DynamicRequire(Kicker.getInstance());
            }
        });
        addSequential(new RetractRoller());
        addSequential(new ExtendPoker());
    }
}

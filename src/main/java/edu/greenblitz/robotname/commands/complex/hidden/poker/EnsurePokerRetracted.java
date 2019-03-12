package edu.greenblitz.robotname.commands.complex.hidden.poker;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.poker.RetractPoker;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.wpi.first.wpilibj.command.Command;

public class EnsurePokerRetracted extends CommandChain {
    @Override
    protected void initChain() {
        addSequential(new WaitAndRequire(Poker.getInstance()));
        if (Poker.getInstance().isRetracted()) {
            addSequential(new NullCommand());
            return;
        }
        addSequential(new DynamicCommand() {
            @Override
            protected Command pick() {
                if (Kicker.getInstance().isOpen())
                    return new EnsureKickerClosed();
                return new DynamicRequire(Kicker.getInstance());
            }
        });
        addSequential(new RetractPoker());
    }
}

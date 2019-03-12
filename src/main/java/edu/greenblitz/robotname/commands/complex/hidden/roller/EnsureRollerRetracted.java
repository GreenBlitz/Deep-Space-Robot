package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.complex.hidden.kicker.EnsureKickerClosed;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.command.dynamic.NullCommand;
import edu.wpi.first.wpilibj.command.Command;

public class EnsureRollerRetracted extends CommandChain {
    @Override
    protected void initChain() {
        addParallel(new WaitAndRequire(Roller.getInstance()), new WaitAndRequire(Elevator.getInstance()));
        if (Roller.getInstance().isRetracted()) {
            addSequential(new NullCommand());
            return;
        }

        addSequential(new DynamicCommand() {
            @Override
            protected Command pick() {
                if (Elevator.getInstance().isFloorLevel() && Kicker.getInstance().isOpen())
                    return new EnsureKickerClosed();
                return new DynamicRequire(Kicker.getInstance());
            }
        });

        addSequential(new RetractRoller());
    }
}

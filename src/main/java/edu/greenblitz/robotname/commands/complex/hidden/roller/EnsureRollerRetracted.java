package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureRollerRetracted extends CommandChain {
    public EnsureRollerRetracted() {
        super(new Unkick());
        addSequential(new RetractRoller());
    }
}

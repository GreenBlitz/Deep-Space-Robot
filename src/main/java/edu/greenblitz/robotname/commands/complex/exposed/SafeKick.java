package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.commands.simple.kicker.Kick;
import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.roller.ExtendRoller;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.WaitAndRequire;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SafeKick extends CommandGroup {
    public SafeKick() {
        addSequential(new WaitAndRequire(Poker.getInstance()));
        addSequential(new ExtendRoller());
        addSequential(new Kick());
        addSequential(new Unkick());
        addSequential(new RetractRoller());
    }
}

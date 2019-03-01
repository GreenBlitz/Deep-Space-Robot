package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.kicker.Unkick;
import edu.greenblitz.robotname.commands.simple.roller.RetractRoller;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class EnsureRollerRetracted extends CommandChain {
    @Override
    protected void initChain() {
        if(!Elevator.getInstance().isFloorLevel())
            addSequential(new Unkick());
        addSequential(new RetractRoller());
    }
}

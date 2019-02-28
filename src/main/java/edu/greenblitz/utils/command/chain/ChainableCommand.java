package edu.greenblitz.utils.command.chain;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.List;

public abstract class ChainableCommand extends GBCommand {

    public void addRequirements(Iterable<Subsystem> systems){
        for (Subsystem s : systems)
            requires(s);
    }

    public abstract List<Subsystem> getRequirements();

}

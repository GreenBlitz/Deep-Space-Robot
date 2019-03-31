package edu.greenblitz.utils.command;

import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Optional;

public class WaitUntilFree extends GBCommand {

    private Subsystem[] systems;

    public WaitUntilFree(Subsystem... subs){
        systems = subs;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        for (Subsystem s : systems){
            if (!s.getCurrentCommandName().equals(s.getDefaultCommandName()))
                return false;
        }
        return true;
    }
}

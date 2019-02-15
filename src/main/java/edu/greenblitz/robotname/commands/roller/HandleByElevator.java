package edu.greenblitz.robotname.commands.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;

public class HandleByElevator extends SubsystemCommand<Roller> {

    public HandleByElevator() {
        super(Roller.getInstance());
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
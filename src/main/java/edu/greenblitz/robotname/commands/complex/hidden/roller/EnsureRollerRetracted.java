package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;

public class EnsureRollerRetracted extends SubsystemCommand<Roller> {
    public EnsureRollerRetracted() {
        super(Roller.getInstance());
    }

    @Override
    protected void initialize() {
        system.retract();
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }
}

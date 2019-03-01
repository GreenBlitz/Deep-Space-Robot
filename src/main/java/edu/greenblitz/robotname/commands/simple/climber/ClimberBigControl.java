package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ClimberBigControl extends SubsystemCommand<Climber.Big> {
    private double power;

    public ClimberBigControl(double power) {
        super(Climber.getInstance().getBig());
        this.power = power;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
        system.lower(power);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

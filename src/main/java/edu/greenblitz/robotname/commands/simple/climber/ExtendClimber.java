package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

public class ExtendClimber extends SubsystemCommand<Climber.Extender> {
    private double power;

    public ExtendClimber(double power) {
        super(Climber.getInstance().getExtender());
        this.power = power;
    }


    @Override
    public State getDeltaState() {
        return new State(null, null, null, null);
    }

    @Override
    protected void execute() {
        system.extend(power);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

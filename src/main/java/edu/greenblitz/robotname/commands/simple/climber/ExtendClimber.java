package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.SubsystemCommand;

public class ExtendClimber extends SubsystemCommand<Climber.Extender> {
    private double power;

    public ExtendClimber(double power) {
        super(Climber.getInstance().getExtender());
        this.power = power;
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

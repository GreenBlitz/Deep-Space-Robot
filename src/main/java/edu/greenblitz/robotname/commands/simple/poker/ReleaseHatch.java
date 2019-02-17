package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class ReleaseHatch extends SubsystemCommand<FrontPoker> {

    public ReleaseHatch() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.setKicker(Value.kForward);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
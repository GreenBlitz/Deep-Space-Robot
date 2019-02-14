package edu.greenblitz.robotname.commands.poker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class PullFrontPoker extends SubsystemCommand<FrontPoker> {

    public PullFrontPoker() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.setExtender(Value.kReverse);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
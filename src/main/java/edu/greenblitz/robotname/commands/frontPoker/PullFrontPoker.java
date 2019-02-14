package edu.greenblitz.robotname.commands.frontPoker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
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
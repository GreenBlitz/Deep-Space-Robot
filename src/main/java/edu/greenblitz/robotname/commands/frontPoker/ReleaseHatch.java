package edu.greenblitz.robotname.commands.frontPoker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
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
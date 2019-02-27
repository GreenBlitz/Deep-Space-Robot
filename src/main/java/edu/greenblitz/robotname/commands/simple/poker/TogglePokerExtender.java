package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.FrontPoker;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class TogglePokerExtender extends SubsystemCommand<FrontPoker> {
    public TogglePokerExtender() {
        super(FrontPoker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(!system.isExtended());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

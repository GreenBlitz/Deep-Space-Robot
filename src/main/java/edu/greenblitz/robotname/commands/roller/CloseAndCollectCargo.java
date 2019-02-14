package edu.greenblitz.robotname.commands.roller;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Roller;

public class CloseAndCollectCargo extends SubsystemCommand<Roller> {
    private static final long timeout = 1000;

    CloseAndCollectCargo() {
        super(Roller.getInstance());
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        system.setExtender(Value.kReverse);
    }

    @Override
    protected void execute() {
        system.setPower(1);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
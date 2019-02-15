package edu.greenblitz.robotname.commands.roller;

import edu.greenblitz.robotname.data.InterwindSubsystems;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class CloseAndCollectCargoByLimitSwitch extends SubsystemCommand<Roller> {
    public CloseAndCollectCargoByLimitSwitch() {
        super(Roller.getInstance());
    }

    @Override
    protected void initialize() {
        system.retract();
    }

    @Override
    protected void execute() {
        system.setPower(0.4);
    }

    @Override
    protected boolean isFinished() {
        return Elevator.getInstance().isBallFullyIn();
    }

    @Override
    protected void end() {
        system.setPower(0);
    }
}
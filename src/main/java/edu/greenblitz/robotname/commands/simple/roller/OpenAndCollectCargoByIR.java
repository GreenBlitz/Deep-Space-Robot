package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class OpenAndCollectCargoByIR extends Command {

    public OpenAndCollectCargoByIR() {
        requires(Roller.getInstance());
    }

    @Override
    protected void initialize() {
        Roller.getInstance().extend();
    }

    @Override
    protected void execute() {
        Roller.getInstance().setPower(0.4);
    }

    @Override
    protected boolean isFinished() {
        return Elevator.getInstance().isBallIn();
    }

    @Override
    protected void end() {
        Scheduler.getInstance().add(new CloseAndCollectCargoByLimitSwitch());
    }
}
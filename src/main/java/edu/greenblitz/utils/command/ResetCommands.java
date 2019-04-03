package edu.greenblitz.utils.command;

import edu.greenblitz.knockdown.Robot;
import edu.greenblitz.knockdown.commands.simple.shifter.KeepShift;
import edu.greenblitz.knockdown.subsystems.Shifter;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ResetCommands extends GBCommand {

    @Override
    protected void atInit() {
        Scheduler.getInstance().removeAll();
    }

    @Override
    protected void atEnd() {
        Shifter.getInstance().setDefaultCommand(new KeepShift());
        Robot.getInstance().getChosenGearCommand().start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

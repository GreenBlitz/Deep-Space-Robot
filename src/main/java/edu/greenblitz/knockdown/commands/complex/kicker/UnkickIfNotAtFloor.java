package edu.greenblitz.knockdown.commands.complex.kicker;

import edu.greenblitz.knockdown.commands.simple.kicker.KickerBaseCommand;
import edu.greenblitz.knockdown.commands.simple.kicker.Unkick;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.base.GBCommand;

public class UnkickIfNotAtFloor extends GBCommand {


    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void atEnd() {
        if (!Elevator.getInstance().isFloorLevel())
            new Unkick().start();
    }
}

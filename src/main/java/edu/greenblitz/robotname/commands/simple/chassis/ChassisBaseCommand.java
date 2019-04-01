package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public abstract class ChassisBaseCommand extends SubsystemCommand<Chassis> {
    public ChassisBaseCommand() {
        super(Chassis.getInstance());
    }

    public ChassisBaseCommand(String name) {
        super(name, Chassis.getInstance());
    }

    public ChassisBaseCommand(long ms) {
        super(ms, Chassis.getInstance());
    }

    public ChassisBaseCommand(String name, long ms) {
        super(name, ms, Chassis.getInstance());
    }

}

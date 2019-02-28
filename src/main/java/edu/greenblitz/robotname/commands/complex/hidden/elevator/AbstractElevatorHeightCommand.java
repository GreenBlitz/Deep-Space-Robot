package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.utils.command.chain.CommandChain;

public abstract class AbstractElevatorHeightCommand extends CommandChain {
    protected double height;

    public AbstractElevatorHeightCommand(double height) {
        this.height = height;
    }

    public AbstractElevatorHeightCommand(String name, double height) {
        super(name);
        this.height = height;
    }

    public AbstractElevatorHeightCommand(double timeout, double height) {
        super(timeout);
        this.height = height;
    }

    public AbstractElevatorHeightCommand(String name, double timeout, double height) {
        super(name, timeout);
        this.height = height;
    }
}

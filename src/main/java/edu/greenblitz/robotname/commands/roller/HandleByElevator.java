package edu.greenblitz.robotname.commands.roller;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Roller;

public class HandleByElevator extends SubsystemCommand<Roller> {

    public HandleByElevator() {
        super(Roller.getInstance());
    }

    @Override
    protected void execute() {
        
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
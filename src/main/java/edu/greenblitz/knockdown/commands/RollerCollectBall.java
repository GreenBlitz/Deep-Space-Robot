package edu.greenblitz.knockdown.commands;

import edu.greenblitz.knockdown.subsystems.Roller;
import edu.wpi.first.wpilibj.command.Command;

public class RollerCollectBall extends Command {

    public RollerCollectBall(){
        requires(Roller.getInstance());
    }

    @Override
    protected void initialize() {
        super.initialize();
        Roller.getInstance().extend();
        Roller.getInstance().roll(1);

    }

    @Override
    protected void end() {
        super.end();
        Roller.getInstance().retract();
        Roller.getInstance().roll(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }


}

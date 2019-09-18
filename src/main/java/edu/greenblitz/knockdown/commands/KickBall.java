package edu.greenblitz.knockdown.commands;

import edu.greenblitz.knockdown.RobotMap;
import edu.greenblitz.knockdown.subsystems.Kicker;
import edu.greenblitz.knockdown.subsystems.Roller;
import edu.wpi.first.wpilibj.command.Command;

public class KickBall extends Command {

    public KickBall(){
        requires(Roller.getInstance());
        requires(Kicker.getInstance());
    }
    @Override
    protected void initialize() {
        super.initialize();
        Roller.getInstance().extend();
        Roller.getInstance().roll(-1);
        try {
            Thread.sleep(1000);
        } catch (Exception e){

        }
        Kicker.getInstance().extend();

    }

    @Override
    protected boolean isFinished() { return false; }
}

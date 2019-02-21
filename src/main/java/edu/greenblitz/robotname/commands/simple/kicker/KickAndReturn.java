package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class KickAndReturn extends TimedCommand {

    private static final long timeout = 400;

    public KickAndReturn() {
        super(timeout / 1000.0);
        requires(Kicker.getInstance());
    }

    @Override
    protected void initialize() {
        Kicker.getInstance().kick();
    }

    @Override
    protected void end() {
        Kicker.getInstance().unkick();
    }
}
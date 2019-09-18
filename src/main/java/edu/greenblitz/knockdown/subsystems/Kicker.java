package edu.greenblitz.knockdown.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Kicker extends Subsystem {
    private static Kicker instance;
    protected DoubleSolenoid kickerSolenoid;

    private Kicker() {
        kickerSolenoid = new DoubleSolenoid(21,2, 4);
    }

    public static Kicker getInstance() {
        return instance;
    }

    public void extend() {
        this.kickerSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        this.kickerSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {

    }
}

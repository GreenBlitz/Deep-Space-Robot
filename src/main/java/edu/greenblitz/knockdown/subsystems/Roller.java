package edu.greenblitz.knockdown.subsystems;

import edu.greenblitz.knockdown.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Roller extends Subsystem {

    public static final double MAX_POWER = 0.2;
    private static Roller instance;

    protected Spark rollerSpark;
    protected DoubleSolenoid rollerSolenoid;

    public static Roller getInstance() {
        if (instance == null) {
            instance = new Roller();
        }
        return instance;
    }

    private Roller() {
        rollerSpark = new Spark(RobotMap.Roller.Motor.ROLLER);
        rollerSolenoid = new DoubleSolenoid(RobotMap.Roller.Solenoid.FORWARD, RobotMap.Roller.Solenoid.REVERSE);
    }

    public void roll(double power) {
        if (Math.abs(power) <= MAX_POWER)
            this.rollerSpark.set(power);
        else
            this.rollerSpark.set(MAX_POWER * Math.signum(power));
    }

    public void extend() {
        this.rollerSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        this.rollerSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
    }
}

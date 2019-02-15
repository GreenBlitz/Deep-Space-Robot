package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.RobotMap.Kicker.*;

public class Kicker extends Subsystem {

    private static Kicker instance;

    private DoubleSolenoid m_piston;

    private Kicker() {
        m_piston = new DoubleSolenoid(2, Solenoid.Forward, Solenoid.Reverse);
    }

    @Override
    public void initDefaultCommand() {
    }

    public static void init() {
        if (instance == null)
            instance = new Kicker();
    }

    public static Kicker getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    public void setState(Value value) {
        if (m_piston.get() != value) Report.pneumaticsUsed(getName());
        m_piston.set(value);
    }

    public void update() {
        SmartDashboard.putString("Kicker::Command", getCurrentCommandName());
        SmartDashboard.putString("Kicker::State", m_piston.get().name());
    }
}
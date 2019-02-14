package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.RobotMap.RearPicker.*;
import edu.greenblitz.robotname.commands.picker.PickByBumbers;

public class RearPicker extends Subsystem {

    private static RearPicker instance;

    private DoubleSolenoid m_piston;

    private RearPicker() {
        m_piston = new DoubleSolenoid(Solenoid.Forward, Solenoid.Reverse);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new PickByBumbers());
    }

    public static void init() {
        if (instance == null)
            instance = new RearPicker();
    }

    public static RearPicker getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    public void setState(Value value) {
        if (m_piston.get() != value) Report.pneumaticsUsed(getName());
        m_piston.set(value);
    }

    public void update() {
        SmartDashboard.putString("RearPicker::Command", getCurrentCommandName());
        SmartDashboard.putString("RearPicker::Piston", m_piston.get().name());
    }
}
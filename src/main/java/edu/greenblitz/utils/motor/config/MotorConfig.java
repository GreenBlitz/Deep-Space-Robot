package edu.greenblitz.utils.motor.config;

public class MotorConfig {
    public Limit output = new Limit();
    public double voltageRamp = Double.NaN;
    public double voltageCompensation = Double.NaN;
    public boolean isCoast;

    public boolean isVoltageRampEnabled() {
        return Double.isFinite(voltageRamp);
    }

    public boolean isVoltageCompensationEnabled() {
        return Double.isFinite(voltageCompensation);
    }

    public boolean isCoast() { return isCoast; }

    public boolean isBreak() { return !isCoast(); }
}

package edu.greenblitz.knockdown.data;

import edu.greenblitz.knockdown.subsystems.Shifter;

/**
 * On many cases (chassis PID kP or min/max output), the actual value varies between exactly two values, depending on
 * the current gear. This is a helper class to implement this dependency in the code.
 */
public class GearDependentDouble {

    public static final double POWER_TO_SPEED = 4;

    public static double fromSpeed(double value) {
        return value * POWER_TO_SPEED;
    }

    public static double fromPower(double value) {
        return value / POWER_TO_SPEED;
    }

    private double m_power;
    private double m_speed;

    /**
     * Accepts a value, which was measured in a certain gear, and extracts the correct Speed/Power based values from it.
     * <p>The conversion method is simple (divide/multiply by 4), but is also only empirical and not measured or based on data.
     * Feel free to change it if you notice any error</p>
     *
     * @param gear  gear at given value
     * @param value value at given gear
     */
    public GearDependentDouble(Shifter.Gear gear, double value) {
        this(gear.isSpeed() ? fromSpeed(value) : value, gear.isSpeed() ? value : fromPower(value));
    }

    public GearDependentDouble(double power, double speed) {
        m_power = power;
        m_speed = speed;
    }

    public double getPower() {
        return m_power;
    }

    public double getSpeed() {
        return m_speed;
    }

    public double getByGear(Shifter.Gear gear) {
        return gear.isSpeed() ? getSpeed() : getPower();
    }

    /**
     *
     * @return the correct value, based on the current shifter gear
     */
    public double getByCurrentGear() {
        return getByGear(Shifter.getInstance().getCurrentGear());
    }
}

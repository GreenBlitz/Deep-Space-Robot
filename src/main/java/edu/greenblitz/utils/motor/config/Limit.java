package edu.greenblitz.utils.motor.config;

public class Limit {
    public double min = Double.NaN;
    public double max = Double.NaN;

    public boolean isValid() {
        return Double.isFinite(min) && Double.isFinite(max) && min <= max;
    }
}

package frc.utils.ctre;

import com.revrobotics.CANSparkMax;

public class SmartSparkMax extends CANSparkMax implements SmartEncoderBase {

    private static final double TICK_SIZE = 0.0238;

    private double nullPosition;

    public SmartSparkMax(int port, MotorType motorType) {
        super(port, motorType);
        reset();
    }

    @Override
    public void reset() {
        nullPosition = getEncoder().getPosition();
    }

    @Override
    public int getQuadraturePosition() {
        return (int)Math.round((nullPosition - getEncoder().getPosition()) / TICK_SIZE);
    }

    @Override
    public double getQuadratureVelocity() {
        return getEncoder().getVelocity();
    }
}
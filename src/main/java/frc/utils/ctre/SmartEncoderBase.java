package frc.utils.ctre;

public interface SmartEncoderBase {
    
    public void reset();

    public int getQuadraturePosition();

    public double getQuadratureVelocity();
}
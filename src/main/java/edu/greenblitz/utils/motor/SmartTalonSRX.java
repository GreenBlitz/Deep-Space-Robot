package edu.greenblitz.utils.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.robotname.subsystems.RearPicker;
import edu.greenblitz.utils.motor.config.*;

public class SmartTalonSRX extends WPI_TalonSRX implements ISmartMotorController<WPI_TalonSRX> {
    private MotorState currentState;

    public SmartTalonSRX(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public boolean isInverted() {
        return getInverted();
    }

    @Override
    public void follow(ISmartMotorController<WPI_TalonSRX> leader) {
        follow(leader);
        s
    }

    @Override
    public void factoryReset() {
        configFactoryDefault();
    }

    @Override
    public void sensorReset() {
        setSelectedSensorPosition(0);
    }

    @Override
    public void configPID(int idx, PIDConfig config) {
        config_kP(idx, config.kP);
        config_kI(idx, config.kI);
        config_kD(idx, config.kD);
        config_kF(idx, config.kF);
        config_IntegralZone(idx, (int) config.integralZone);
        configAllowableClosedloopError(idx, (int) config.error);

    }

    @Override
    public void configSmartMotion(int idx, SmartMotionConfig config) {
        configPID(idx, config);
        configMotionCruiseVelocity((int) (10 * config.cruiseVelocity));
        configMotionAcceleration((int) (10 * config.maxAcceleration));
    }

    @Override
    public double getRawPower() {
        return get();
    }

    @Override
    public double getCurrent() {
        return getCurrent();
    }

    @Override
    public void set(MotorState state, double value) {
        switch (state) {
            case RAW:
                set(ControlMode.PercentOutput, value);
                break;
            case FOLLOW:
                set(ControlMode.Follower, value);
                break;
            case DISABLED:
                stopMotor();
                break;
            case CURRENT:


        }
    }

    @Override
    public void setMainLoop(int idx) {
        selectProfileSlot(idx, 0);
    }

    @Override
    public MotorState getCurrentState() {
        return null;
    }

    @Override
    public MotorConfig getMotorConfig() {
        return null;
    }
}

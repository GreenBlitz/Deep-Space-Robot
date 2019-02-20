package edu.greenblitz.utils.motor;

import edu.greenblitz.utils.motor.config.*;

/**
 * Base class which allows to use the advanced features of our motor controllers better.
 * Unlike in their configuration, everything here is assumed to be in SI units, and and percentages are between -1 to 1.
 * @param <T> Inner motor type (used for {@link ISmartMotorController#getMotor() getter} and {@link ISmartMotorController#follow(ISmartMotorController) follow}
 */
public interface ISmartMotorController<T> {
    boolean isInverted();

    void setInverted(boolean inverted);

    void setSensorPhase(boolean inverted);

    void follow(ISmartMotorController<T> leader);

    void factoryReset();

    void sensorReset();

    /**
     * Configure a pid closed loop
     * @param idx loop index (usually between 0 to 3)
     * @param config loop configuration
     */
    void configPID(int idx, PIDConfig config);

    /**
     * Configures smart motion mode. Unlike {@link ISmartMotorController#configPID(int, PIDConfig)}, the V and A parts
     * of the configuration are global - you only attach the pid configuration to the loop index
     * @param idx loop index (usually between 0 to 3)
     * @param config loop configuration
     */
    void configSmartMotion(int idx, SmartMotionConfig config);

    double getRawPower();

    double getCurrent();

    double getTemperature();

    void set(MotorState state, double value);

    default void set(double value) {
        set(MotorState.RAW, value);
    }

    default void stop() {
        set(MotorState.DISABLED, 0);
    }

    void setMainLoop(int idx);

    MotorState getCurrentState();

    MotorConfig getMotorConfig();

    T getMotor();
}

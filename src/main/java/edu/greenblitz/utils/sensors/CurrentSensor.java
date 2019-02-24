package edu.greenblitz.utils.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class CurrentSensor extends AnalogInput {

    public CurrentSensor(int channel) {
        super(channel);
    }

    //Viout = Ip/5 + 2.5
    //Viout - 2.5 = Ip/5
    //Ip = 5Viout - 12.5

    public double getCurrent() {
        return 5 * getVoltage() - 12.5;
        //return (getVoltage() - 2.5) * 75 / 2.5;
    }

    public double getAverageCurrent() {
        return 5 * getAverageVoltage() - 12.5;
        //return (getAverageVoltage() - 2.5) * 75 / 2.5;
    }
}
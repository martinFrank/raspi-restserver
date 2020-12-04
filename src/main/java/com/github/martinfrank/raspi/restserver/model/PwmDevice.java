package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PwmDevice extends BaseDevice{

    public final int min;
    public final int max;
    public final int pinAddress;
    public final String pinName;
    public int pwmValue;
    public double pwmPercent;

    public PwmDevice(String name, String unit, String notes, String pinName, int min, int max) {
        super(name, unit, notes);
        this.min = min;
        this.max = max;
        this.pinName = pinName;
        this.pinAddress = RaspiPin.getPinByName(pinName).getAddress();
    }

    public int getPwmValue(double pwmPercent) {
        if (pwmPercent > 1) {
            pwmPercent = 1;
        }
        if (pwmPercent < -1) {
            pwmPercent = -1;
        }
        double center = (max + min) / 2d;
        double range = (max - min) / 2d;
        return (int) (pwmPercent * range + center);
    }

    @Override
    public void set(String value, String unit) {
        if (DeviceControlCommand.UNIT_STOP.equalsIgnoreCase(unit)) {
            stopPwm();
        } else if (DeviceControlCommand.UNIT_PERCENT.equalsIgnoreCase(unit)) {
            setPwm(value);
        } else {
            throw new IllegalArgumentException("unknown unit");
        }
    }

    public abstract void setPwm(String value);

    public abstract void stopPwm() ;
}

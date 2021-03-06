package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.pi4j.wiringpi.Gpio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardwarePwmDevice extends PwmDevice{

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwarePwmDevice.class);

    private  final int hardwarePwmRange = 2000;

    public HardwarePwmDevice(String name, String unit, String notes, String pinName, int min, int max, boolean isInverted) {
        super(name, unit, notes, pinName, min, max, isInverted);
    }

    public HardwarePwmDevice(RaspiRestServerConfiguration.DeviceConfiguration deviceConfiguration) {
        this(deviceConfiguration.name,
                deviceConfiguration.unit,
                deviceConfiguration.notes,
                deviceConfiguration.hardwarePwmConfiguration.pin,
                deviceConfiguration.hardwarePwmConfiguration.min,
                deviceConfiguration.hardwarePwmConfiguration.max,
                deviceConfiguration.hardwarePwmConfiguration.invert);
    }

    @Override
    public void setPwm(String value) {
        pwmPercent = Double.parseDouble(value);
        pwmValue = getPwmValue(pwmPercent);
        Gpio.pwmWrite(pinAddress, pwmValue);
        LOGGER.info("PWM Pin:" + pinName + " value:" + pwmValue + "/" + hardwarePwmRange);
    }

    @Override
    public void stopPwm() {
        Gpio.pwmWrite(pinAddress, 0);
        LOGGER.info("PWM Pin:" + pinName + " value:0/" + hardwarePwmRange);
    }

    @Override
    public String getValue() {
        return unit+":"+pwmPercent;
    }
}

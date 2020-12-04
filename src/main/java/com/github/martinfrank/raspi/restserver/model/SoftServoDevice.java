package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.pi4j.wiringpi.SoftPwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoftServoDevice extends PwmDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoftServoDevice.class);
    public static final int SOFT_PWM_RANGE = 200;


    public SoftServoDevice(String name, String unit, String notes, String pinName, int min, int max) {
        super(name, unit, notes, pinName, min, max);
        SoftPwm.softPwmCreate(pinAddress, 0, 200);
    }

    public SoftServoDevice(RaspiRestServerConfiguration.DeviceConfiguration deviceConfiguration) {
        this(deviceConfiguration.name,
                deviceConfiguration.unit,
                deviceConfiguration.notes,
                deviceConfiguration.softServoConfiguration.pin,
                deviceConfiguration.softServoConfiguration.min,
                deviceConfiguration.softServoConfiguration.max);
    }

    @Override
    public void setPwm(String value) {
        pwmPercent = Double.parseDouble(value);
        pwmValue = getPwmValue(pwmPercent);
        SoftPwm.softPwmWrite(pinAddress, pwmValue);
        LOGGER.info("PWM Pin:" + pinName + " value:" + pwmValue + "/" + SOFT_PWM_RANGE);
    }

    @Override
    public void stopPwm() {
        SoftPwm.softPwmWrite(pinAddress, 0);
        LOGGER.info("PWM Pin:" + pinName + " value:0/" + SOFT_PWM_RANGE);
    }

    @Override
    public String getValue() {
        return unit + ":" + pwmPercent;
    }
}

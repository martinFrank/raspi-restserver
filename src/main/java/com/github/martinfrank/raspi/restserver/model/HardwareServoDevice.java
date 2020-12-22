package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiController;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.wiringpi.SoftPwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardwareServoDevice extends BaseDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareServoDevice.class);
    public static final int SOFT_PWM_RANGE = 200;
    private final int min;
    private final int max;
    private final boolean isInverted;
    private double pwmPercent;
    private final String pinName;
    final PwmDriver pwmDriver;

    public HardwareServoDevice(RaspiRestServerConfiguration.DeviceConfiguration deviceConfiguration, RaspiController raspiController) {
        super(deviceConfiguration.name,
                deviceConfiguration.unit,
                deviceConfiguration.notes);
        this.min = deviceConfiguration.hardwareServoConfiguration.min;
        this.max = deviceConfiguration.hardwareServoConfiguration.max;
        this.isInverted = deviceConfiguration.hardwareServoConfiguration.invert;
        pinName = deviceConfiguration.hardwareServoConfiguration.pin;
        pwmDriver = new PwmDriver(raspiController, pinName);
    }


    public void setPwm(String value) {
        pwmPercent = Double.parseDouble(value);
        pwmPercent = isInverted ? pwmPercent * -1d : pwmPercent;
        pwmDriver.setPwm(pwmPercent, min, max);
        LOGGER.info("PWM Pin:" + pinName + " value: " + PercentFormatter.format(pwmPercent));
    }


    public void stopPwm() {
        pwmDriver.stopPwm();
        LOGGER.info("PWM Pin:" + pinName + " value:0/" + SOFT_PWM_RANGE);
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

    @Override
    public String getValue() {
        return unit + ":" + pwmPercent;
    }
}

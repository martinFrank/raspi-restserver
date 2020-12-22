package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiController;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.SoftPwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

public class SoftMotorDevice extends BaseDevice
{

    private static final Logger LOGGER = LoggerFactory.getLogger(SoftMotorDevice.class);
    public static final int SOFT_PWM_RANGE = 200;
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("##.##%");

    private final GpioPinDigitalOutput directionalPin;
    public final int drivePinAddress;
    public final String drivePinName;
    public int pwmValue;
    public double pwmPercent;
    private boolean isInverted;

    public SoftMotorDevice(RaspiRestServerConfiguration.DeviceConfiguration configuration, RaspiController controller) {
        super(configuration.name,
                configuration.unit,
                configuration.notes);
        directionalPin = controller.getGpioController().provisionDigitalOutputPin(
                RaspiPin.getPinByName(configuration.softMotorConfiguration.directionalPin));
        this.drivePinName = configuration.softMotorConfiguration.drivePin;
        this.drivePinAddress = RaspiPin.getPinByName(drivePinName).getAddress();
        SoftPwm.softPwmCreate(drivePinAddress, 0, 200);
        this.isInverted = configuration.softMotorConfiguration.invert;
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
        //TODO
        return null;
    }

    public void setPwm(String raw) {
        pwmPercent = Double.parseDouble(raw);
        pwmPercent = isInverted ? pwmPercent * -1d : pwmPercent;
        pwmPercent = pwmPercent > 1 ? 1 : pwmPercent;
        pwmPercent = pwmPercent < -1 ? -1 : pwmPercent;
        directionalPin.setState(!(pwmPercent < 0));
        pwmValue = (int)(200d*(Math.abs(pwmPercent)));
        SoftPwm.softPwmWrite(drivePinAddress, pwmValue);
        LOGGER.info("drive Pin:" + drivePinName + " value:" + pwmValue + "/" + SOFT_PWM_RANGE
                +" ("+PERCENT_FORMAT.format(pwmPercent*100)+")");
        LOGGER.info("directional Pin:" + directionalPin.getName() + " value:" + directionalPin.isHigh());
    }

    public void stopPwm() {
        SoftPwm.softPwmWrite(drivePinAddress, 0);
        LOGGER.info("PWM Pin:" + drivePinName + " value:0/" + SOFT_PWM_RANGE+" (STOP)");

    }
}

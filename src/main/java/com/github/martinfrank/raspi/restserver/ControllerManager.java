package com.github.martinfrank.raspi.restserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.Gpio;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerManager implements Managed {

    private final GpioController gpioController;
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerManager.class);

    public ControllerManager(RaspiRestServerConfiguration configuration) {
        Gpio.wiringPiSetup();
        gpioController = GpioFactory.getInstance();
        Gpio.pwmSetRange(configuration.pwmSetting.hardwarePwmRange);
        Gpio.pwmSetClock(configuration.pwmSetting.hardwarePwmClock);
        Gpio.pwmSetMode(getPwmMode(configuration.pwmSetting.hardwarePwmMode));
        LOGGER.debug("gpio successfully created");
    }

    @Override
    public void start() throws Exception {
        LOGGER.debug("gpio successfully started");
    }

    @Override
    public void stop() throws Exception {
        gpioController.shutdown();
        LOGGER.debug("gpio successfully stopped");
    }

    private int getPwmMode(String pwmMode) {
        if ("PWM_MODE_BAL".equalsIgnoreCase(pwmMode)){
            return Gpio.PWM_MODE_BAL;
        }

        //default/invalid mode:
        return Gpio.PWM_MODE_MS;
    }

    public GpioController getController() {
        return gpioController;
    }
}
